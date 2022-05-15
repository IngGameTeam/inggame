package io.github.inggameteam.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.event.GameBeginEvent
import io.github.inggameteam.player.hasTags
import org.bukkit.event.EventHandler

interface BeginPlayersAmount : Game {

    var beginPlayersAmount: Int

    @Suppress("unused")
    @EventHandler
    fun beginPlayersAmount(event: GameBeginEvent) {
        if (event.game !== this) return
        beginPlayersAmount = joined.hasTags(PTag.PLAY).size
    }

}