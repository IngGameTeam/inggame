package io.github.inggameteam.inggame.minigame.base.gameserver

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent

class DisableCollision(
    private val gameServer: GameServer,
    val plugin: IngGamePlugin
) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onBoatCollision(event: VehicleEntityCollisionEvent) {
        if (isNotHandler(gameServer)) return
        event.isCancelled = true
    }

}