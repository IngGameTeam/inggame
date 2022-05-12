package io.github.inggameteam.minigame.angangang.game.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.event.GameBeginEvent
import io.github.inggameteam.scheduler.delay
import org.bukkit.event.EventHandler

interface TimerToEnd : Game {

    @Deprecated("EventHandler")
    @EventHandler
    fun timerToEnd(event: GameBeginEvent) {
        if (this !== event.game) return
        addTask({
            stop(true)
        }.delay(plugin, comp.int("timer-to-end").toLong()))
    }

}