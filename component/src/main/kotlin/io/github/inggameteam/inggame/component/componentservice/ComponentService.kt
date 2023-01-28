
package io.github.inggameteam.inggame.component.componentservice

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.NameSpaceNotFoundException
import io.github.inggameteam.inggame.component.delegate.uncoverDelegate
import java.util.concurrent.CopyOnWriteArraySet
import kotlin.reflect.KClass

@Suppress("NAME_SHADOWING")
interface ComponentService {

    val name: String
    val parentComponent: ComponentService
    val layerPriority: Int

    @Suppress("UNCHECKED_CAST")
    operator fun <T : Any> get(nameSpace: Any, key: Any, clazz: KClass<T>): T {
//        println("!!$name")
        val nameSpace = uncoverDelegate(nameSpace)
        val ns = getAll().firstOrNull { it.name == nameSpace }
            ?: run {
                try { return parentComponent[nameSpace, key, clazz] } catch (_: Throwable) { }
                throw NameSpaceNotFoundException(nameSpace)
            }
        return ns.elements.getOrDefault(key, null)?.run { this as T }
            ?: run {
                ns.parents.forEach { try { return get(it, key, clazz) } catch (_: Throwable) { } }
                throw NameSpaceNotFoundException(nameSpace)
            }
    }

    fun findComponentService(nameSpace: Any): ComponentService {
        val nameSpace = uncoverDelegate(nameSpace)
        val ns = getAll().firstOrNull { it.name == nameSpace }
        if (ns !== null) return this
        try { return parentComponent.findComponentService(nameSpace) } catch (_: Throwable) { }
        throw NameSpaceNotFoundException(nameSpace)
    }

    fun sortParentsByPriority(parents: CopyOnWriteArraySet<Any>): CopyOnWriteArraySet<Any> {
        return parents.map { Pair(it, findComponentService(it)) }
            .sortedWith { o1, o2 -> o1.second.layerPriority.compareTo(o2.second.layerPriority) }
            .map { it.first }.run(::CopyOnWriteArraySet)
    }

    fun has(nameSpace: Any, key: Any): Boolean =
        try { get(nameSpace, key, Any::class); true } catch (_: Throwable) { false }

    fun set(nameSpace: Any, key: Any, value: Any?)

    fun setParents(name: Any, value: Collection<Any>)
    fun getParents(name: Any): CopyOnWriteArraySet<Any>
    fun addParents(name: Any, value: Any)
    fun removeParents(name: Any, value: Any)
    fun hasParents(name: Any, value: Any): Boolean

    fun newModel(name: Any): NameSpace
    operator fun get(name: Any): NameSpace
    fun getOrNull(name: Any): NameSpace?
    fun getAll(): Collection<NameSpace>



}