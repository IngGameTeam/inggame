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
        return get(nameSpace).let { obj ->
            obj.elements[key]?.run { try { this as T } catch (_: Exception) {
                throw AssertionError("'$nameSpace' uuid '$key' key is not ${clazz.simpleName} model")}
            }?: run {
                obj.parents.forEach {
                    try { return@run component[it, key, clazz] } catch (_: NameSpaceNotFoundException) { }
                }
                throw AssertionError("'$nameSpace' uuid '$key' key ${clazz.simpleName} model is not exists")
            }
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

    override fun getAll(): ArrayList<NameSpace> {
        return ArrayList(objectList)
    }

}