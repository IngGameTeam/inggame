package io.github.inggameteam.inggame.component.componentservice

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.NameSpaceNotFoundException
import io.github.inggameteam.inggame.component.decodeNameSpace
import io.github.inggameteam.inggame.component.encodeNameSpace
import io.github.inggameteam.inggame.mongodb.MongoCodec
import io.github.inggameteam.inggame.mongodb.MongoRepo
import io.github.inggameteam.inggame.utils.IngGamePlugin
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArraySet
import kotlin.reflect.KClass

class ResourcesComponentServiceImp(
    private val repo: MongoRepo,
    private val codec: MongoCodec,
    private val component: ComponentService,
    plugin: IngGamePlugin
) : ResourceComponentService, AbstractNameSpaceComponentService() {

    private lateinit var nameSpaceCache: ArrayList<NameSpace>
    private var semaphore = false

    init {
        poolNameSpace()
        plugin.addDisableEvent { saveAll() }
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
        getNameSpaces().map { ns -> encodeNameSpace(ns, codec) }.apply { repo.set(this) }
        semaphore = false
    }

    private fun findNameSpace(repo: MongoRepo, codec: MongoCodec): ArrayList<NameSpace> {
        return repo.get().map { doc -> decodeNameSpace(doc, codec) }.run(::ArrayList)
    }

    override fun getNameSpaces() =
        if (this::nameSpaceCache.isInitialized) nameSpaceCache else {
            poolNameSpace()
            nameSpaceCache
        }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> get(nameSpace: Any, key: Any, clazz: KClass<T>): T {
        val ns = getNameSpaces().firstOrNull { it.name == nameSpace }
            ?: run {
                try { return component[nameSpace, key, clazz] } catch (_: NameSpaceNotFoundException) { }
                throw NameSpaceNotFoundException(nameSpace)
            }
        return ns.elements.getOrDefault(key, null)?.run { this as T }
            ?: run {
                ns.parents.forEach { try { return get(it, key, clazz) } catch (_: NameSpaceNotFoundException) { } }
                throw AssertionError("'$nameSpace' namespace '$key' key does not exist")
            }
    }

    override fun has(nameSpace: Any, key: Any): Boolean =
        try { get(nameSpace, key, Any::class); true } catch (_: Throwable) { false }

    override fun getOrNull(name: Any) = getNameSpaces()
        .firstOrNull { it.name == name }
        ?: NameSpace(name, CopyOnWriteArraySet(), ConcurrentHashMap()).apply { nameSpaceCache.add(this) }

    override fun set(nameSpace: Any, key: Any, value: Any?) {
        val ns = getOrNull(nameSpace)
        if (value === null) ns.elements.remove(key) else ns.elements[key] = value
    }

}