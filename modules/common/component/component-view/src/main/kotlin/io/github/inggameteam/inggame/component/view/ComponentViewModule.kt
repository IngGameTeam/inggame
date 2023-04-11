package io.github.inggameteam.inggame.component.view

import io.github.bruce0203.gui.GuiWindow
import io.github.inggameteam.inggame.component.event.ComponentLoadEvent
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.Listener
import org.bukkit.event.EventHandler

class ComponentViewModule(val plugin: IngGamePlugin) : Listener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onRegisterComponentService(event: ComponentLoadEvent) {
        plugin.addDisableEvent {
            plugin.server.onlinePlayers.filter { it.openInventory.topInventory.holder is GuiWindow }
                .forEach { it.closeInventory() }
        }
    }
}