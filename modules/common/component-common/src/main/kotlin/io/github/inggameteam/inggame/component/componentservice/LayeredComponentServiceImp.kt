package io.github.inggameteam.inggame.component.componentservice

import io.github.inggameteam.inggame.component.Assert
import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.decodeNameSpace
import io.github.inggameteam.inggame.component.encodeNameSpace
import io.github.inggameteam.inggame.component.wrapper.uncoverDelegate
import io.github.inggameteam.inggame.mongodb.MongoCodec
import io.github.inggameteam.inggame.mongodb.MongoRepo
import java.util.concurrent.CopyOnWriteArraySet

@Suppress("NAME_SHADOWING")
open class LayeredComponentServiceImp(
    private val repo: MongoRepo,
    private val codec: MongoCodec,
    override val parentComponent: ComponentService,
    override val name: String,
) : LayeredComponentService, AbstractNameSpaceComponentService() {

    private val objectList = CopyOnWriteArraySet<NameSpace>()

    override fun toString(): String {
        return objectList.toString()
    }

    override fun getOrNull(name: Any): NameSpace? {
        val name = uncoverDelegate(name)
        return objectList.firstOrNull { it.name == name }
    }

    override fun saveAll() {
        getAll().forEach { save(it.name) }
    }

    override fun set(nameSpace: Any, key: Any, value: Any?) {
        val nameSpace = uncoverDelegate(nameSpace)
        val ns = get(nameSpace)
        if (value === null) ns.elements.remove(key)
        else ns.elements[key] = value
    }

    override fun load(name: Any, new: Boolean) {
        val name = uncoverDelegate(name)
        if (objectList.contains(name))
            throw Assert("an error occurred while load namespace due to already exist")
        val doc = if (new) null else repo.get(name)
        objectList.add(
            if (doc === null) newModel(name)
            else decodeNameSpace(doc, codec)
        )
    }

    override fun unload(name: Any, save: Boolean) {
        val name = uncoverDelegate(name)
        val obj = getOrNull(name)
        if (obj === null) return
        if (save) repo.set(name, encodeNameSpace(obj, codec))
        objectList.remove(obj)
    }

    override fun save(name: Any) {
        repo.set(name, encodeNameSpace(get(name), codec))
    }

    override fun getAll(): List<NameSpace> {
        return ArrayList(objectList)
    }

    override fun removeNameSpace(name: Any) {
        objectList.removeIf { it.name == name }
    }

    override fun addNameSpace(name: Any) {
        if (objectList.none { it.name == name }) objectList.add(newModel(name))
    }

}