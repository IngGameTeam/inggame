package io.github.inggameteam.inggame.component.view

import io.github.inggameteam.inggame.component.event.ComponentServiceRegisterEvent
import io.github.inggameteam.inggame.component.view.handler.ViewPlayerLoader
import io.github.inggameteam.inggame.component.view.wrapper.Selector
import io.github.inggameteam.inggame.utils.HandleListener
import org.bukkit.event.EventHandler
import org.bukkit.plugin.Plugin
import org.koin.core.qualifier.named
import org.koin.dsl.module

class ComponentViewModule(plugin: Plugin) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onRegisterComponentService(event: ComponentServiceRegisterEvent) {
        event.registerClass(Selector::class)
        event.addModule(module(createdAtStart = true) { single { ViewPlayerLoader(get(named("view-player")), get()) } })
        event.register {
            "view-player" isLayer true cs "view-resource" isSavable true cs "default"
        }
    }
}