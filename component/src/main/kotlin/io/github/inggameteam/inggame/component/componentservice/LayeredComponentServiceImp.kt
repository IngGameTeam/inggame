package io.github.inggameteam.inggame.component.componentservice

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.NameSpaceNotFoundException
import io.github.inggameteam.inggame.component.decodeNameSpace
import io.github.inggameteam.inggame.component.encodeNameSpace
import io.github.inggameteam.inggame.mongodb.MongoCodec
import io.github.inggameteam.inggame.mongodb.MongoRepo
import java.util.concurrent.CopyOnWriteArraySet
import kotlin.reflect.KClass

open class LayeredComponentServiceImp(
    private val repo: MongoRepo,
    private val codec: MongoCodec,
    private val component: ComponentService
) : LayeredComponentService, AbstractNameSpaceComponentService() {

    private val objectList = CopyOnWriteArraySet<NameSpace>()

    override fun toString(): String {
        return objectList.toString()
    }

    override fun getOrNull(name: Any): NameSpace? = objectList.firstOrNull { it.name == name }

    override fun saveAll() {
        getAll().forEach { save(it.name) }
    }

    override fun set(nameSpace: Any, key: Any, value: Any?) {
        val ns = getOrNull(nameSpace)?: newModel(nameSpace)
        if (value === null) ns.elements.remove(key)
        else ns.elements[key] = value
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> get(nameSpace: Any, key: Any, clazz: KClass<T>): T {
        val ns = objectList.firstOrNull { it.name == nameSpace }
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


    override fun load(name: Any) {
        val doc = repo.get(name)
        objectList.add(
            if (doc === null) newModel(name)
            else decodeNameSpace(doc, codec)
        )
    }

    override fun unload(name: Any, save: Boolean) {
        val obj = getOrNull(name)
        if (obj === null) return
        if (save) repo.set(name, encodeNameSpace(obj, codec))
        objectList.remove(obj)
    }

    override fun save(name: Any) {
        repo.set(name, encodeNameSpace(get(name), codec))
    }

    override fun getAll(): Collection<NameSpace> {
        return ArrayList(objectList)
    }

}