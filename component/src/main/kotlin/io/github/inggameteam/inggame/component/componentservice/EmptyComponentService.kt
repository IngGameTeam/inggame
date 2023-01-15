package io.github.inggameteam.inggame.component.componentservice

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.NameSpaceNotFoundException
import kotlin.reflect.KClass

interface EmptyComponentService : ComponentService

class EmptyComponentServiceImp : EmptyComponentService, AbstractNameSpaceComponentService() {
    override fun <T : Any> get(nameSpace: Any, key: Any, clazz: KClass<T>): T {
        throw NameSpaceNotFoundException(nameSpace)
    }

    override val parentComponent: ComponentService
        get() = throw AssertionError("error occurred while perform parentComponent variable to empty component")
    override val layerPriority: Int
        get() = throw AssertionError("error occurred while perform layerPriority variable to empty component")

    override fun has(nameSpace: Any, key: Any): Boolean {
        throw AssertionError("error occurred while perform has method to empty component")
    }

    override fun set(nameSpace: Any, key: Any, value: Any?) {
        throw AssertionError("error occurred while set to empty component")
    }

    override fun getOrNull(name: Any): NameSpace? {
        throw NameSpaceNotFoundException(name)
    }

    override fun getAll(): Collection<NameSpace> {
        throw AssertionError("error occurred while get all to empty component")
    }

}