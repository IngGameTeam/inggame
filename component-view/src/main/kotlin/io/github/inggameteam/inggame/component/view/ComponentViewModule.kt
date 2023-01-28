package io.github.inggameteam.inggame.component.view

import io.github.inggameteam.inggame.component.event.ComponentServiceRegisterEvent
import io.github.inggameteam.inggame.component.view.handler.ViewPlayerLoader
import io.github.inggameteam.inggame.utils.HandleListener
import org.bukkit.event.EventHandler
import org.bukkit.plugin.Plugin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

class ComponentViewModule(plugin: Plugin) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onRegisterComponentService(event: ComponentServiceRegisterEvent) {
        event.addModule(module(createdAtStart = true) {
            singleOf(::ViewPlayerLoader)
        })
        event.registerResource("view-resource")
        event.registerInstance("view-player")

    }
}