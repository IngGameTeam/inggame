package io.github.inggameteam.minigame.angangang.game.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.event.GameBeginEvent
import io.github.inggameteam.player.hasTags
import io.github.inggameteam.scheduler.delay
import org.bukkit.event.EventHandler

interface TimerToTpAll : Game {

    @Deprecated("EventHandler")
    @EventHandler
    fun timerToTpAll(event: GameBeginEvent) {
        if (this !== event.game) return
        addTask({
            joined.hasTags(PTag.PLAY).forEach { it.teleport(getLocation("tp-all")) }
        }.delay(plugin, 20 * comp.int("timer-to-end").toLong()))
    }

}