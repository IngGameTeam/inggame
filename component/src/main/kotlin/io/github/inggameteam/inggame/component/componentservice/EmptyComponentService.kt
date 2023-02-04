package io.github.inggameteam.inggame.component.componentservice

import io.github.inggameteam.inggame.component.Assert
import io.github.inggameteam.inggame.component.NameSpaceNotFoundException
import kotlin.reflect.KClass

interface EmptyComponentService : ComponentService

private val assertionError = Assert("error occurred while perform empty component")
class EmptyComponentServiceImp(override val name: String) : EmptyComponentService {
    override fun <T : Any> find(nameSpace: Any, key: Any, clazz: KClass<T>) = throw NameSpaceNotFoundException(nameSpace)
    override fun get(name: Any) = throw assertionError
    override val parentComponent get() = this
    override val layerPriority get() = 0
    override fun has(nameSpace: Any, key: Any) = throw assertionError
    override fun set(nameSpace: Any, key: Any, value: Any?) = throw assertionError

    override fun setParents(name: Any, value: Collection<Any>) = throw assertionError
    override fun getParents(name: Any) = throw assertionError

    override fun addParents(name: Any, value: Any) = throw assertionError
    override fun removeParents(name: Any, value: Any) = throw assertionError
    override fun hasParents(name: Any, value: Any) = throw assertionError
    override fun newModel(name: Any) = throw assertionError

    override fun getOrNull(name: Any) = throw NameSpaceNotFoundException(name)
    override fun getAll() = throw assertionError
    override fun removeNameSpace(name: Any) = throw assertionError
    override fun addNameSpace(name: Any) = throw assertionError

}