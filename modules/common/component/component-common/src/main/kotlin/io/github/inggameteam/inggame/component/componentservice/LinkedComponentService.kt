package io.github.inggameteam.inggame.component.componentservice

import io.github.inggameteam.inggame.component.NameSpace
import kotlin.reflect.KClass

class LinkedComponentService(
    override val parentComponent: ComponentService,
    override val name: String
) : ComponentService, AbstractNameSpaceComponentService() {

    override fun <T : Any> find(nameSpace: Any, key: Any, clazz: KClass<T>): T {
        return parentComponent.find(nameSpace, key, clazz)
    }

    override fun findComponentService(nameSpace: Any): ComponentService {
        return parentComponent.findComponentService(nameSpace)
    }

    override fun set(nameSpace: Any, key: Any, value: Any?) {
        parentComponent.set(nameSpace, key, value)
    }

    override fun getOrNull(name: Any): NameSpace? {
        return parentComponent.getOrNull(name)
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
