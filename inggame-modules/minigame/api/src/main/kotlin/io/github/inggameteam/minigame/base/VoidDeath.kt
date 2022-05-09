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
        println("voidDeath #1")
        if (!isJoined(player)) return
        println("voidDeath #2")
        val gPlayer = plugin[player]
        println("voidDeath #3")
        if (gameState !== GameState.PLAY || !gPlayer.hasTag(PTag.PLAY)) return
        println("voidDeath #4")
        if (player.location.y <= plugin.gameRegister.sectorHeight - 1) {
            println("voidDeath #5")
            player.damage(10000.0)
            return
        }
    }

}