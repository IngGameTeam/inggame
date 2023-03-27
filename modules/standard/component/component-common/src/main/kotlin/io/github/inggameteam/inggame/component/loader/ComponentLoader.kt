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
    val csModules = eventDsl.registry.map(ComponentServiceDSL::createComponentModule)
    val modules = listOf(*csModules.toTypedArray(), *event.modules.toTypedArray())
    return module {
        includes(modules)
        val component = "component"
        val dsl = ComponentServiceDSL.newRoot().apply {
            cs(component, isSavable = true)
        }
        includes(dsl.registry.map(ComponentServiceDSL::createComponentModule))
        println("-".repeat(10))
        println(dsl.registry.joinToString("\n"))
        println("-".repeat(10))
        println(dsl.registry.filter { it.name == "game-multi" })
        println("-".repeat(10))
        factory {
            val componentService = get<ComponentService>(named(component))
            getKoin().loadModules(componentService.getAll(::ComponentImp).mapNotNull {
                try {
                    dsl.cs(
                        name = it.nameSpace.toString(),
                        type = it.componentType,
                        isSavable = it.isSavable
                    ).apply {
                        if (it.parents.isNotEmpty()) {
                            parents.removeAll(listOf("handler", "default"))
                            parents.addAll(it.parents)
                        }
                    }
                } catch (_: Throwable) { null }
            }.map { it.createComponentModule() })
            getKoin().createEagerInstances()
            ComponentLoader()
        } bind ComponentLoader::class
    }
}