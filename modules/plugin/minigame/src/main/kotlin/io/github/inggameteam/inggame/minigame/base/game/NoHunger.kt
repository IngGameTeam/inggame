package io.github.inggameteam.inggame.minigame.base.game

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import io.github.inggameteam.inggame.minigame.component.GamePlayerService
import io.github.inggameteam.inggame.minigame.event.GPlayerSpawnEvent
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.FoodLevelChangeEvent

class NoHunger(
    private val gamePlayerService: GamePlayerService,
    plugin: IngGamePlugin
) : HandleListener(plugin) {



    @Suppress("unused")
    @EventHandler(priority = EventPriority.MONITOR)
    fun onSpawn(event: GPlayerSpawnEvent) {
        if (isNotHandler(event.player)) return
        event.player.foodLevel = 20
    }

    @Suppress("unused")
    @EventHandler(priority = EventPriority.MONITOR)
    fun onHungry(event: FoodLevelChangeEvent) {
        if (event.entityType != EntityType.PLAYER) return
        val player = gamePlayerService[event.entity.uniqueId, ::GPlayer]
        if (isNotHandler(player)) return
        event.foodLevel = 20
    }

}