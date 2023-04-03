package io.github.inggameteam.inggame.component.loader

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.MultiParentsComponentService
import io.github.inggameteam.inggame.component.event.ComponentLoadEvent
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

class ComponentLoader
fun loadComponents(plugin: IngGamePlugin): Module {
    val eventDsl = ComponentServiceDSL.newRoot()
    val event = ComponentLoadEvent(eventDsl.cs("root"))
    plugin.server.pluginManager.callEvent(event)
    return module {
        includes(event.modules)
        val component = "component"
        val dsl = eventDsl.apply {
            cs(component, isSavable = true)
        }
        includes(dsl.registry.map(ComponentServiceDSL::createComponentModule))
        factory {
            val componentService = get<ComponentService>(named(component))
            getKoin().loadModules(componentService.getAll(::ComponentImp).mapNotNull {
                try {
                    val existingCS = get<ComponentService>(named(it.nameSpace.toString()))
                    if (existingCS is MultiParentsComponentService) {
                        existingCS.components.addAll(it.componentParentList.map { p -> get(named(p)) })
                    }
                }
                catch (_: Throwable) { }
                try {
                    dsl.cs(
                        name = it.nameSpace.toString(),
                        type = it.componentType,
                        isSavable = it.isSavable
                    ).apply {
                        if (it.componentParentList.isNotEmpty()) {
                            parents.removeAll(listOf("handler", "default"))
                            parents.add("handler")
                            parents.addAll(it.componentParentList)
                        }
                    }
                } catch (_: Throwable) { null }
            }.map { it.createComponentModule() })
            println("-".repeat(10))
            println(event.componentServiceRegistry.registry.joinToString("\n"))
            println("-".repeat(10))

            getKoin().createEagerInstances()
            ComponentLoader()
        } bind ComponentLoader::class
    }
}