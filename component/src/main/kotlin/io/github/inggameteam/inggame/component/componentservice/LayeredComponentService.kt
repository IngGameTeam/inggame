package io.github.inggameteam.inggame.component.componentservice

import io.github.inggameteam.inggame.component.NameSpaceNotFoundException
import io.github.inggameteam.inggame.component.delegate.uncoverDelegate
import kotlin.reflect.KClass

interface LayeredComponentService : ComponentService, SaveComponentService {

    fun load(name: Any, new: Boolean = false)
    fun unload(name: Any, save: Boolean)
    fun save(name: Any)

    @Suppress("UNCHECKED_CAST")
    override operator fun <T : Any> get(nameSpace: Any, key: Any, clazz: KClass<T>): T {
        val nameSpace = uncoverDelegate(nameSpace)
        val ns = getAll().firstOrNull { it.name == nameSpace } ?: run { throw NameSpaceNotFoundException(nameSpace) }
        return ns.elements.getOrDefault(key, null)?.run { this as T }
            ?: run {
                ns.parents.forEach { try { return parentComponent[it, key, clazz]
                } catch (_: Throwable) { } }
                throw NameSpaceNotFoundException(nameSpace)
            }
    }

}