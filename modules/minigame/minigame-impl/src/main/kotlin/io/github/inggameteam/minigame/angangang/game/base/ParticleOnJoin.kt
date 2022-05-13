package io.github.inggameteam.minigame.angangang.game.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.event.GameJoinEvent
import org.bukkit.Particle
import org.bukkit.event.EventHandler

interface ParticleOnJoin : Game {

    @Deprecated("EventHandler")
    @EventHandler
    fun onJoinParticle(event: GameJoinEvent) {
        val player = event.player
        if (!isJoined(player)) return
        player.spawnParticle(Particle.TOTEM, player.location, 10)
    }

}