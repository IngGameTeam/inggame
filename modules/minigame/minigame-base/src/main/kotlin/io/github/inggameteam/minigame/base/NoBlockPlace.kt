package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.GameState
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockPlaceEvent

interface NoBlockPlace : Game {

    @Suppress("unused")
    @EventHandler
    fun noBlockPlace(event: BlockPlaceEvent) {
        val player = event.player
        if (!isJoined(player) && gameState !== GameState.WAIT)
            event.isCancelled = true
    }

}