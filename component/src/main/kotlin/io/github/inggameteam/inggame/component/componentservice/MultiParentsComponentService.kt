package io.github.inggameteam.inggame.component.componentservice

import io.github.inggameteam.inggame.component.NameSpace
import kotlin.reflect.KClass

class MultiParentsComponentService(
    override val name: String,
    override val parentComponent: ComponentService,
    private val components: Collection<ComponentService>,
    private val defaultComponent: ComponentService,
    private val componentKey: Any
) : ComponentService, AbstractNameSpaceComponentService() {

    override fun <T : Any> get(nameSpace: Any, key: Any, clazz: KClass<T>): T {
        val component = try { parentComponent[nameSpace, componentKey, Any::class]
            .let { name -> components.firstOrNull { it.name == name }?: defaultComponent } }
        catch (_: Throwable) { defaultComponent }
        return component[nameSpace, key, clazz]
    }

    override fun set(nameSpace: Any, key: Any, value: Any?) = parentComponent.set(nameSpace, key, value)

    override fun getOrNull(name: Any): NameSpace? = parentComponent.getOrNull(name)

    override fun getAll(): Collection<NameSpace> = parentComponent.getAll()

}