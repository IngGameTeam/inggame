package io.github.inggameteam.minigame.handle

import io.github.inggameteam.api.HandleListener
import org.bukkit.event.EventHandler
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent
import org.bukkit.plugin.Plugin


class DisableCollision(val plugin: Plugin) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onBoatCollision(event: VehicleEntityCollisionEvent) {
        event.isCollisionCancelled = true
        event.isCancelled = true
    }

}