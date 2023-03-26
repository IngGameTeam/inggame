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
    println(eventDsl.registry.joinToString("\n"))
    val modules = listOf(*csModules.toTypedArray(), *event.modules.toTypedArray())
    return module {
        includes(modules)
        val component = "component"
        val dsl = ComponentServiceDSL.newRoot().apply {
            cs(component, isSavable = true)
        }
//        includes(dsl.registry.map(ComponentServiceDSL::createComponentModule))
//        factory {
//            val componentService = get<ComponentService>(named(component))
//            getKoin().loadModules(componentService.getAll(::ComponentImp).map {
//                dsl.cs(
//                    name = it.nameSpace.toString(),
//                    type = it.componentType,
//                    isSavable = it.isSavable
//                )
//            }.map { it.createComponentModule() })
//            getKoin().createEagerInstances()
//            ComponentLoader()
//        } bind ComponentLoader::class
    }
}