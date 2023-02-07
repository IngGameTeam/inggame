package io.github.inggameteam.inggame.component.componentservice

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.decodeNameSpace
import io.github.inggameteam.inggame.component.wrapper.uncoverDelegate
import io.github.inggameteam.inggame.component.encodeNameSpace
import io.github.inggameteam.inggame.mongodb.MongoCodec
import io.github.inggameteam.inggame.mongodb.MongoRepo
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArraySet

@Suppress("NAME_SHADOWING")
class ResourceComponentServiceImp(
    private val repo: MongoRepo,
    private val codec: MongoCodec,
    override val parentComponent: ComponentService,
    override val name: String,
) : ResourceComponentService, AbstractNameSpaceComponentService() {

    private lateinit var nameSpaceCache: CopyOnWriteArraySet<NameSpace>
    private var semaphore = false

    override fun toString(): String {
        return nameSpaceCache.toString()
    }

    init {
        poolNameSpace()
    }

    override fun poolNameSpace() {
        if (semaphore) return
        semaphore = true
        nameSpaceCache = findNameSpace(repo, codec)
        semaphore = false
    }

    override fun saveAll() {
        if (semaphore) return
        semaphore = true
        getAll().map { ns -> encodeNameSpace(ns, codec) }.apply { repo.set(this) }
        semaphore = false
    }

    private fun findNameSpace(repo: MongoRepo, codec: MongoCodec): CopyOnWriteArraySet<NameSpace> {
        return repo.get().map { doc -> decodeNameSpace(doc, codec) }.run(::CopyOnWriteArraySet)
    }

    override fun getAll(): List<NameSpace> =
        if (this::nameSpaceCache.isInitialized) nameSpaceCache else {
            poolNameSpace()
            nameSpaceCache
        }.toList()

    override fun removeNameSpace(name: Any) {
        nameSpaceCache.removeIf { it.name == name }
    }

    override fun addNameSpace(name: Any) {
        if (nameSpaceCache.none { it.name == name }) nameSpaceCache.add(newModel(name))
    }

    override fun getOrNull(name: Any) = getAll().firstOrNull { it.name == name }

    override fun set(nameSpace: Any, key: Any, value: Any?) {
        val nameSpace = uncoverDelegate(nameSpace)
        val ns = getOrNull(nameSpace)
            ?: NameSpace(name, CopyOnWriteArraySet(), ConcurrentHashMap()).apply { nameSpaceCache.add(this) }
        if (value === null) ns.elements.remove(key) else ns.elements[key] = value
    }

}