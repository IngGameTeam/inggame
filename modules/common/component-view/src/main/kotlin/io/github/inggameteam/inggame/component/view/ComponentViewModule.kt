package io.github.inggameteam.inggame.component.view

import io.github.bruce0203.gui.GuiWindow
import io.github.inggameteam.inggame.component.classOf
import io.github.inggameteam.inggame.component.event.ComponentLoadEvent
import io.github.inggameteam.inggame.component.loader.ComponentServiceType
import io.github.inggameteam.inggame.component.view.handler.ViewPlayerLoader
import io.github.inggameteam.inggame.component.view.wrapper.Selector
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.Listener
import org.bukkit.event.EventHandler
import org.koin.core.qualifier.named
import org.koin.dsl.module

class ComponentViewModule(val plugin: IngGamePlugin) : Listener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onRegisterComponentService(event: ComponentLoadEvent) {
        event.registerClass {
            classOf(::EditorRegistry)
            classOf(Selector::class)
        }
        event.addModule(module(createdAtStart = true) {
            single {
                ViewPlayerLoader(get(named("view-player")), get())
            }
        })
        event.componentServiceDSL
            .cs("view-player", type = ComponentServiceType.MASKED)
            .cs("view-resource", isSavable = true)
        plugin.addDisableEvent {
            plugin.server.onlinePlayers.filter { it.openInventory.topInventory.holder is GuiWindow }
                .forEach { it.closeInventory() }
        }
    }
}