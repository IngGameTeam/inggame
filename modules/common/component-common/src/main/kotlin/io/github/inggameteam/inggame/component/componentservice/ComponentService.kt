
package io.github.inggameteam.inggame.component.componentservice

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.NameSpaceNotFound
import io.github.inggameteam.inggame.component.wrapper.NonNullWrapperImp
import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.component.wrapper.uncoverDelegate
import io.github.inggameteam.inggame.utils.fastFirstOrNull
import io.github.inggameteam.inggame.utils.fastForEach
import java.util.concurrent.CopyOnWriteArraySet
import kotlin.reflect.KClass

@Suppress("NAME_SHADOWING")
interface ComponentService {

    val name: String
    val parentComponent: ComponentService
    val layerPriority: Int

    fun find(nameSpace: Any, key: Any): Any {
        println("1")
        val nameSpace = uncoverDelegate(nameSpace)
        val ns = getAll().fastFirstOrNull { it.name == nameSpace }
            ?: run {
                try { return parentComponent.find(nameSpace, key) } catch (_: Throwable) { }
                throw NameSpaceNotFound
            }
        return ns.elements.getOrDefault(key, null)
            ?: run {
                ns.parents.toArray().fastForEach { try { return find(it, key) } catch (_: Throwable) { } }
                throw NameSpaceNotFound
            }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> find(nameSpace: Any, key: Any, clazz: KClass<T>): T {
        return find(nameSpace, key) as T
    }


    fun findComponentService(nameSpace: Any): ComponentService {
        val nameSpace = uncoverDelegate(nameSpace)
        val ns = getAll().fastFirstOrNull { it.name == nameSpace }
        if (ns !== null) return this
        try { return parentComponent.findComponentService(nameSpace) } catch (_: Throwable) { }
        throw NameSpaceNotFound
    }

    fun sortParentsByPriority(parents: CopyOnWriteArraySet<Any>): CopyOnWriteArraySet<Any> {
        return parents.map { Pair(it, findComponentService(it)) }
            .sortedWith { o1, o2 -> o1.second.layerPriority.compareTo(o2.second.layerPriority) }
            .map { it.first }.run(::CopyOnWriteArraySet)
    }

    fun has(nameSpace: Any, key: Any): Boolean =
        try { find(nameSpace, key); true } catch (_: Throwable) { false }

    fun set(nameSpace: Any, key: Any, value: Any?)

    fun setParents(name: Any, value: Collection<Any>)
    fun getParents(name: Any): CopyOnWriteArraySet<Any>
    fun addParents(name: Any, value: Any)
    fun removeParents(name: Any, value: Any)
    fun hasParents(name: Any, value: Any): Boolean {
        val name = uncoverDelegate(name)
        val parents = getOrNull(name)?.parents
        if (parents === null) return false
        if (parents.contains(value)) return true
        return parents.any { findComponentService(it).hasParents(it, value) }
    }

    fun newModel(name: Any): NameSpace
    operator fun get(name: Any): NameSpace
    fun getOrNull(name: Any): NameSpace?
    fun getAll(): List<NameSpace>

    fun <T> getAll(block: (Wrapper) -> T): List<T> {
        return getAll().map { get(it.name, block) }
    }

    fun removeNameSpace(name: Any)
    fun addNameSpace(name: Any)

    operator fun <T> get(nameSpace: Wrapper, block: (Wrapper) -> T): T {
        val ns = uncoverDelegate(nameSpace)
        return block(NonNullWrapperImp(ns, this))
    }

    operator fun <T> get(nameSpace: Any, block: (Wrapper) -> T): T {
        val ns = uncoverDelegate(nameSpace)
        return block(NonNullWrapperImp(ns, this))
    }

}