package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.event.GPlayerSpawnEvent
import io.github.inggameteam.minigame.event.GameJoinEvent
import org.bukkit.Particle
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority

interface ParticleOnSpawn : Game {

    @Suppress("unused")
    @EventHandler(priority = EventPriority.MONITOR)
    fun onJoinParticle(event: GPlayerSpawnEvent) {
        val player = event.player
        if (!isJoined(player)) return
        player.location.world?.spawnParticle(Particle.TOTEM, player.location, 10)
    }

}