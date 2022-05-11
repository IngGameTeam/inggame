package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.*
import io.github.inggameteam.minigame.event.GameJoinEvent
import io.github.inggameteam.player.hasTags
import io.github.inggameteam.scheduler.delay
import org.bukkit.event.EventHandler

interface StartPlayersAmountAlert : Game {

    @EventHandler
    fun startPlayersAmountAlert(event: GameJoinEvent) {
        val player = event.player
        if (!isJoined(player)) return
        {
            if (gameState == GameState.WAIT
                && gameTask === null
                && joined.hasTags(PTag.PLAY).size == 1
                && startPlayersAmount != 1
            ) joined.forEach { comp.send(GameAlert.NEED_PLAYER, it, displayName(it), startPlayersAmount) }
        }.delay(plugin, 0)
    }

}