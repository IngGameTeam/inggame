package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.event.GameUnloadEvent
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler

interface ClearEntitiesOnStop : Sectional {

    @Suppress("unused")
    @EventHandler
    fun onGameStop(event: GameUnloadEvent) {
        point.world.getNearbyEntities(getLocation("start"), 100.0, 100.0, 100.0).forEach {
            if (it.type != EntityType.PLAYER) it.remove()
        }
    }

}