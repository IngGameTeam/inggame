package io.github.inggameteam.inggame.minigame.view

import io.github.inggameteam.inggame.component.classOf
import io.github.inggameteam.inggame.component.event.ComponentLoadEvent
import io.github.inggameteam.inggame.minigame.view.gamechoosingmenu.GameChoosingMenu
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.Listener
import org.bukkit.event.EventHandler

class GameViewModule(plugin: IngGamePlugin) : Listener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onLoadComponent(event: ComponentLoadEvent) {
        event.registerClass {
            classOf(::GameChoosingMenu)
        }
    }

}