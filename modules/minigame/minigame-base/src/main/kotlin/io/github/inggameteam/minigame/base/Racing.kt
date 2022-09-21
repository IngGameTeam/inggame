package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.event.GPlayerDeathEvent
import io.github.inggameteam.minigame.event.GPlayerSpawnEvent
import io.github.inggameteam.minigame.event.GameLeftEvent
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.scheduler.delay
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.vehicle.VehicleExitEvent

abstract class Racing(plugin: GamePlugin) : CompetitionImpl(plugin), SpawnPlayer, NoInteract, Respawn {

    abstract fun getRider(): Class<out Entity>
    override val recommendedSpawnDelay: Long get() = -1L

    @Suppress("unused")
    @EventHandler(priority = EventPriority.HIGHEST)
    fun spawnRider(event: GPlayerSpawnEvent) {
        val player = event.player
        if (!isJoined(player)) return
        if (gameState === GameState.PLAY) {
            removeRider(player)
            player.world.spawn(player.location.add(0.0, 0.4, 0.0), getRider()) { entity ->
                {
                    entity.addPassenger(player.bukkit)
                }.delay(plugin, 1)
                entity.addScoreboardTag(RIDER_TAG)
            }.apply { playerData[player]!![RIDER_KEY] = this }
        }

    }

    @Suppress("unused")
    @EventHandler(priority = EventPriority.LOWEST)
    fun onDeathRemoveRider(event: GPlayerDeathEvent) {
        val player = event.player
        if (!isJoined(player)) return
        removeRider(player)

    }

    @Suppress("unused")
    @EventHandler
    fun removeRiderOnLeave(event: GameLeftEvent) {
        val player = event.player
        if (!isJoined(player)) return
        removeRider(player)
    }

    private fun removeRider(player: GPlayer) { (playerData[player]!![RIDER_KEY] as? Entity)?.remove() }

    @Suppress("unused")
    @EventHandler
    fun offRider(event: VehicleExitEvent) {
        val exited = event.exited
        if (exited is Player) {
            if (isJoined(exited)) {
                event.isCancelled = true
            }
        }
    }

    companion object {
        const val RIDER_TAG = "Rider"
        const val RIDER_KEY = "RiderKey"
    }
}