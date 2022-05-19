package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.GameAlert
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.event.GameJoinEvent
import io.github.inggameteam.player.hasTags
import io.github.inggameteam.scheduler.delay
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority

interface StartPlayersAmountAlert : Game {

    @EventHandler(priority = EventPriority.HIGH)
    fun startPlayersAmountAlert(event: GameJoinEvent) {
        val player = event.player
        if (!isJoined(player)) return
        {
            if (gameState === GameState.WAIT
                && joined.hasTags(PTag.PLAY).size < startPlayersAmount
                && startPlayersAmount > 1
            ) joined.forEach { comp.send(GameAlert.NEED_PLAYER, it, displayName(it), startPlayersAmount) }
        }.delay(plugin, 0)
    }

}