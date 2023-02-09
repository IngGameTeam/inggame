package io.github.inggameteam.inggame.item

import io.github.inggameteam.inggame.component.event.IngGamePluginLoadEvent
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.Listener
import org.bukkit.event.EventHandler

class ItemModule(plugin: IngGamePlugin) : Listener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onEnable(event: IngGamePluginLoadEvent) {
//        event.register {
//            this
//                .cs("item-player", isMask = true)
//                .cs("item-resource", isMulti = true, key = "item-language", root = "player-instance").csc {
//                    cs("item-template-korean", isSavable = true)
//                }
//        }
    }

}