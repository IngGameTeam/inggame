package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.event.GameUnloadEvent
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler

interface ClearEntitiesOnStop : Sectional {

    @Suppress("unused")
    @EventHandler
    fun onGameStop(event: GameUnloadEvent) {
        point.world.getNearbyEntities(Location(point.world,
            point.x + plugin.gameRegister.sectorWidth.toDouble(),
            plugin.gameRegister.sectorHeight.toDouble(),
            point.y + plugin.gameRegister.sectorWidth.toDouble()
        ), 150.0, 150.0, 150.0).forEach {
            if (it.type != EntityType.PLAYER) it.remove()
        }
    }

}