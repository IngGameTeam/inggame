package io.github.inggameteam.inggame.component.componentservice

import io.github.inggameteam.inggame.component.NameSpaceNotFoundException
import kotlin.reflect.KClass

interface EmptyComponentService : ComponentService

class EmptyComponentServiceImp(override val name: String) : EmptyComponentService {
    override fun <T : Any> get(nameSpace: Any, key: Any, clazz: KClass<T>) = throw NameSpaceNotFoundException(nameSpace)
    override fun get(name: Any) = throw AssertionError("error occurred while perform empty component")
    override val parentComponent get() = this
    override val layerPriority get() = 0
    override fun has(nameSpace: Any, key: Any) = throw AssertionError("error occurred while perform empty component")
    override fun set(nameSpace: Any, key: Any, value: Any?) =
        throw AssertionError("error occurred while perform empty component")

    override fun setParents(name: Any, value: Collection<Any>) = throw AssertionError("error occurred while perform empty component")
    override fun getParents(name: Any) = throw AssertionError("error occurred while perform empty component")

    override fun addParents(name: Any, value: Any) =
        throw AssertionError("error occurred while perform empty component")

    override fun removeParents(name: Any, value: Any) =
        throw AssertionError("error occurred while perform empty component")

    override fun hasParents(name: Any, value: Any) =
        throw AssertionError("error occurred while perform empty component")

    override fun newModel(name: Any) = throw AssertionError("error occurred while perform empty component")

    override fun getOrNull(name: Any) = throw NameSpaceNotFoundException(name)
    override fun getAll() = throw AssertionError("error occurred while perform empty component")
    override fun removeNameSpace(name: Any) = throw AssertionError("error occurred while perform empty component")
    override fun addNameSpace(name: Any) = throw AssertionError("error occurred while perform empty component")

}