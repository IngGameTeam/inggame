package io.github.inggameteam.minigame.base

import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.vehicle.VehicleExitEvent
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.event.GPlayerDeathEvent
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.hasTags

abstract class Racing(plugin: GamePlugin) : CompetitionImpl(plugin) {

    abstract fun getRider(): Class<out Entity>

    override fun beginGame() {
        super.beginGame()
        var i = 0
        joined.hasTags(PTag.PLAY).apply { shuffle() }.forEach { player ->
            spawn(player, i)
            i++
            i %= comp.intOrNull("location-size")?: 1

        }
    }

    fun spawn(player: GPlayer, slot: Int = 0) {
        player.teleport(getLocation("$slot"))
        player.world.spawn(player.location.add(0.0, 0.4, 0.0), getRider()) {
            it.addPassenger(player)
            it.addScoreboardTag(RIDER_TAG)
        }
    }


    @Suppress("unused")
    @EventHandler
    fun move(event: PlayerMoveEvent) {
        val player = event.player
        if (isJoined(player)) {
            if (player.location.apply {
                    if (block.type == Material.AIR) add(0.0, -1.0, 0.0)
                }.block.type == Material.LIME_CONCRETE) {
                joined.hasTags(PTag.PLAY).filter { player != it }
                    .forEach { it.damage(10000.0) }
            }
        }
    }

    @Suppress("unused")
    @EventHandler
    fun voidFallRespawn(event: GPlayerDeathEvent) {
        val player = event.player
        if (!isJoined(player)) return
        val gPlayer = plugin[player]
        if (gameState !== GameState.PLAY || !gPlayer.hasTag(PTag.PLAY)) return
        player.vehicle?.remove()
        spawn(gPlayer)
    }

    @Suppress("unused")
    @EventHandler
    fun damage(event: EntityDamageByEntityEvent) {
        val player = event.damager
        if (gameState === GameState.PLAY && player is Player && isJoined(player)) event.isCancelled = true
    }

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
    }
}