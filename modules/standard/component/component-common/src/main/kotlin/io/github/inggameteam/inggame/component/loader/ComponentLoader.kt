package io.github.inggameteam.inggame.component.loader

import io.github.inggameteam.inggame.component.componentservice.ComponentService
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
        val component = "component"
        val dsl = eventDsl.apply {
            cs(component, isSavable = true)
        }
        factory {
            val componentService = get<ComponentService>(named(component))
            componentService.getAll(::ComponentImp).mapNotNull {
                try {
                    dsl.cs(
                        name = it.nameSpace.toString(),
                        type = it.componentType,
                        isSavable = it.isSavable
                    ).apply {
                        if (it.parentsList.isNotEmpty()) {
                            parents.removeAll(listOf("handler", "default"))
                            parents.addAll(it.parentsList)
                        }
                    }
                } catch (_: Throwable) { null }
            }
            getKoin().loadModules(dsl.registry.map(ComponentServiceDSL::createComponentModule))
            getKoin().loadModules(event.modules)
            println("-".repeat(10))
            println(event.componentServiceRegistry.registry.joinToString("\n"))
            println("-".repeat(10))
            println(event.componentServiceRegistry.registry.filter { it.name == "game-multi" })
            println("-".repeat(10))

            getKoin().createEagerInstances()
            ComponentLoader()
        } bind ComponentLoader::class
    }
}