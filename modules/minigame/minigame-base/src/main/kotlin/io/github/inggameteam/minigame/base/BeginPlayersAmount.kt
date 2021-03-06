package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.event.GameBeginEvent
import io.github.inggameteam.player.hasTags
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority

interface BeginPlayersAmount : Game {

    var beginPlayersAmount: Int

    @Suppress("unused")
    @EventHandler(priority = EventPriority.HIGHEST)
    fun beginPlayersAmount(event: GameBeginEvent) {
        if (event.game !== this) return
        beginPlayersAmount = joined.hasTags(PTag.PLAY).size
    }

}