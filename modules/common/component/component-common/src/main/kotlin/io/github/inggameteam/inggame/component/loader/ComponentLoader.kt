package io.github.inggameteam.inggame.component.loader

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.MultiParentsComponentService
import io.github.inggameteam.inggame.component.event.ComponentLoadEvent
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

object ComponentLoader
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
            val componentsList = componentService.getAll(::ComponentImp)
            getKoin().loadModules(componentsList.mapNotNull {
                if (runCatching { get<ComponentService>(named(it.nameSpace.toString())) }.isSuccess)
                    null
                else try {
                    val componentParentList = try { it.componentParentList } catch (_: Throwable) { emptyList() }
                    dsl.cs(
                        name = it.nameSpace.toString(),
                        type = try { it.componentType } catch (_: Throwable) { ComponentServiceType.RESOURCE },
                        isSavable = try { it.isSavable } catch(_: Throwable) { true }
                    ).apply {
                        if (componentParentList.isNotEmpty()) {
                            parents.remove("default")
                            parents.remove("handler")
                            parents.addAll(componentParentList)
                        } else {
                            parents.remove("default")
                            parents.add("handler")
                        }
                    }
                } catch(e: Throwable) { null }
            }.map { it.createComponentModule() })
            componentsList.forEach {
                try {
                    val existingCS = get<ComponentService>(named(it.nameSpace.toString()))
                    if (existingCS is MultiParentsComponentService) {
                        existingCS.components.addAll(0, it.componentParentList.map { p -> get(named(p)) })
                    }
                }
                catch (_: Throwable) { }
            }

            getKoin().createEagerInstances()
            ComponentLoader
        } bind ComponentLoader::class
    }
}