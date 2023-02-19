package io.github.inggameteam.inggame.item

import io.github.inggameteam.inggame.component.classOf
import io.github.inggameteam.inggame.component.event.ComponentLoadEvent
import io.github.inggameteam.inggame.component.event.newModule
import io.github.inggameteam.inggame.component.loader.ComponentServiceType
import io.github.inggameteam.inggame.item.component.ItemResource
import io.github.inggameteam.inggame.item.handler.UseItem
import io.github.inggameteam.inggame.item.wrapper.Item
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.Listener
import org.bukkit.event.EventHandler

class ItemModule(plugin: IngGamePlugin) : Listener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onEnable(event: ComponentLoadEvent) {
        event.registerClass {
            classOf(Item::class)
            classOf(::UseItem)
        }
        event.addModule(newModule("item-resource", ::ItemResource))
        event.componentServiceDSL.apply {
            this
                .cs("item-player", type = ComponentServiceType.LAYER)
                .cs("item-resource",
                    type = ComponentServiceType.MULTI, key = "item-language", root = "player-instance"
                ) csc { cs("item-template-korean", isSavable = true).cs("handler") }
        }

    }
}