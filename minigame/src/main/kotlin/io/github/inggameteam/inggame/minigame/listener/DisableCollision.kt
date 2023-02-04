package io.github.inggameteam.inggame.minigame.listener

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.Listener
import org.bukkit.event.EventHandler
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent

class DisableCollision(val plugin: IngGamePlugin) : Listener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onBoatCollision(event: VehicleEntityCollisionEvent) {
        event.isCollisionCancelled = true
        event.isCancelled = true
    }

}