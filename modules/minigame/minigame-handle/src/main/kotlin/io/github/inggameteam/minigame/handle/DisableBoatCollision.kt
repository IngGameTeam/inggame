package io.github.inggameteam.minigame.handle

import io.github.inggameteam.api.HandleListener
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent
import org.bukkit.plugin.Plugin


class DisableBoatCollision(val plugin: Plugin) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onBoatCollision(event: VehicleEntityCollisionEvent) {
        val vehicle: Entity = event.entity
        if (vehicle.type == EntityType.BOAT) {
            event.isCollisionCancelled = true
            event.isCancelled = true
        }
    }

}