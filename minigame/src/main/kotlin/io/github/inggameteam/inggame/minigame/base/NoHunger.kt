package io.github.inggameteam.inggame.minigame.base

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.FoodLevelChangeEvent

class NoHunger(plugin: IngGamePlugin) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler(priority = EventPriority.MONITOR)
    fun onHungry(event: FoodLevelChangeEvent) {
        event.foodLevel = 20
    }

}