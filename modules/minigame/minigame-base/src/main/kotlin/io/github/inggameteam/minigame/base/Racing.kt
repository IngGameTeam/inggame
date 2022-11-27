package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.event.GPlayerDeathEvent
import io.github.inggameteam.minigame.event.GPlayerSpawnEvent
import io.github.inggameteam.minigame.event.GameLeftEvent
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.hasTags
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.vehicle.VehicleDestroyEvent
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent
import org.bukkit.event.vehicle.VehicleExitEvent

abstract class Racing(plugin: GamePlugin) : CompetitionImpl(plugin), SpawnPlayer, Respawn {

    abstract fun getRider(): Class<out Entity>
    override val recommendedSpawnDelay: Long get() = -1L
    open fun entityCode(entity: Entity) {}

    @Suppress("unused")
    @EventHandler(priority = EventPriority.HIGHEST)
    fun spawnRider(event: GPlayerSpawnEvent) {
        val player = event.player
        if (!isJoined(player)) return
        if (gameState === GameState.PLAY && player.hasTag(PTag.PLAY)) {
            removeRider(player)
            player.world.spawn(player.location.add(0.0, 0.4, 0.0), getRider()) { entity ->
                entity.addPassenger(player.bukkit)
                entity.addScoreboardTag(RIDER_TAG)
                entityCode(entity)
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

    @Suppress("unused")
    @EventHandler
    fun onRiderDamage(event: EntityDamageEvent) {
        if (event.entity.scoreboardTags.contains(RIDER_TAG)) {
            event.isCancelled = true
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onRiderDestroy(event: VehicleDestroyEvent) {
        val attacker = event.attacker?.uniqueId
        if (joined.hasTags(PTag.PLAY).any { attacker == it.uniqueId }) {
            event.isCancelled = true
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onRiderDamage(event: VehicleEntityCollisionEvent) {
        if (event.entity.scoreboardTags.contains(RIDER_TAG)) {
            event.isCancelled = true
            event.isCollisionCancelled = true
        }
    }


    companion object {
        const val RIDER_TAG = "Rider"
        const val RIDER_KEY = "RiderKey"
    }
}