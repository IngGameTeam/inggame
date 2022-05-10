package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.PTag
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerMoveEvent

interface VoidDeath : Game {

    @Deprecated("EventHandler")
    @EventHandler
    fun voidDeath(event: PlayerMoveEvent) {
        val player = event.player
        if (!isJoined(player)) return
        val gPlayer = plugin[player]
        if (gameState !== GameState.PLAY || !gPlayer.hasTag(PTag.PLAY)) return
        if (player.location.y <= plugin.gameRegister.sectorHeight - 1) {
            player.damage(1000.0)
            return
        }
    }

}