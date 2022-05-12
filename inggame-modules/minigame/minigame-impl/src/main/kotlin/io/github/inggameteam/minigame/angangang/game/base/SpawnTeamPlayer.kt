package io.github.inggameteam.minigame.angangang.game.base

import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.event.GPlayerSpawnEvent
import org.bukkit.event.EventHandler

interface SpawnTeamPlayer : SpawnPlayer {

    @EventHandler
    override fun spawnPlayer(event: GPlayerSpawnEvent) {
        val player = event.player
        if (!isJoined(player)) return
        val gPlayer = plugin[player]
        if (gameState === GameState.WAIT) {
            super.spawn(gPlayer, gameState.toString())
            return
        }
        if (gPlayer.hasTag(PTag.RED)) spawn(player, PTag.RED.toString())
        else if (gPlayer.hasTag(PTag.BLUE)) spawn(player, PTag.BLUE.toString())
    }

}