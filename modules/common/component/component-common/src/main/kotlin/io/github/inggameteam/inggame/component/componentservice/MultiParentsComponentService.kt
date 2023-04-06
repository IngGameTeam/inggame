package io.github.inggameteam.inggame.component.componentservice

import io.github.inggameteam.inggame.component.Assert
import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.NameSpaceNotFound
import io.github.inggameteam.inggame.component.wrapper.uncoverDelegate
import io.github.inggameteam.inggame.utils.fastFirstOrNull
import io.github.inggameteam.inggame.utils.fastForEach
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.reflect.KClass

@Suppress("NAME_SHADOWING")
class MultiParentsComponentService(
    override val name: String,
    private val rootComponent: () -> ComponentService?,
    var components: CopyOnWriteArrayList<ComponentService>,
    private val parentKey: Any?
) : ComponentService, AbstractNameSpaceComponentService() {

    init {
        if (components.isEmpty()) {
            throw Assert("an error occurred while create multi parents component service that empty components collection")
        }
        components = components.sortedWith { o1, o2 -> o1.layerPriority.compareTo(o2.layerPriority) }.run(::CopyOnWriteArrayList)
    }

    override val parentComponent get() = components.first()

    override val layerPriority: Int by lazy { return@lazy components.maxOf { it.layerPriority } }

    private fun findParent(nameSpace: Any): List<ComponentService> {
        if (parentKey === null) return components
        return try { rootComponent()!!.find(nameSpace, parentKey)
            .let { name -> components.fastFirstOrNull { it.name == name }!! }.run(::listOf) }
        catch (_: Throwable) { listOf(components.first()) }
//        return components
    }

    private fun <T, R> List<T>.firstSuccess(block: (T) -> R, throws: Throwable): R {
        this.fastForEach { try { return block(it) } catch (_: Throwable) { } }
        throw throws
    }

    override fun <T : Any> find(nameSpace: Any, key: Any, clazz: KClass<T>): T {
        if (parentKey == key) throw NameSpaceNotFound
        val nameSpace = uncoverDelegate(nameSpace)
        return findParent(nameSpace).firstSuccess({ it.find(nameSpace, key, clazz) }, NameSpaceNotFound)
    }

    override fun findComponentService(nameSpace: Any): ComponentService {
        val nameSpace = uncoverDelegate(nameSpace)
        val ns = getAll().fastFirstOrNull { it.name == nameSpace }
        if (ns !== null) return this
        return findParent(nameSpace).firstSuccess({ it.findComponentService(nameSpace) }, NameSpaceNotFound)
    }

    override fun set(nameSpace: Any, key: Any, value: Any?) {
        findParent(nameSpace).first().set(nameSpace, key, value)
    }

    override fun getOrNull(name: Any): NameSpace? {
        return findParent(name).firstSuccess({ getOrNull(name) }, NameSpaceNotFound)
    }

    override fun getAll(): List<NameSpace> {
        return emptyList()
    }

    override fun removeNameSpace(name: Any) {
        //nothing
    }

    override fun addNameSpace(name: Any) {
        //nothing
    }

}