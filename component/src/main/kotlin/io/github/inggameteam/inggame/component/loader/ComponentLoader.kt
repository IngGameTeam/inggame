package io.github.inggameteam.inggame.component.loader

import io.github.inggameteam.inggame.component.ComponentServiceDSL
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.createComponentModule
import io.github.inggameteam.inggame.component.event.IngGamePluginLoadEvent
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

class ComponentLoader
fun loadComponents() = module(createdAtStart = true) {
    val component = "component"
    val dsl = ComponentServiceDSL.newRoot()
    includes(dsl.registry.map(ComponentServiceDSL::createComponentModule))
    factory {
        val componentService = get<ComponentService>(named(component))
        getKoin().loadModules(componentService.getAll(::ComponentImp).map {
            dsl.cs(
                name = it.nameSpace.toString(),
                type = it.componentType,
                isSavable = it.isSavable
            )
        }.map { it.createComponentModule() })
        get<IngGamePlugin>().server.pluginManager.callEvent(IngGamePluginLoadEvent(dsl))
        ComponentLoader()
    } bind ComponentLoader::class
}