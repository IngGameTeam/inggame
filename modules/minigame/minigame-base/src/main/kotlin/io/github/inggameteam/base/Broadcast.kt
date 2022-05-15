package io.github.inggameteam.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.event.GameBeginEvent
import io.github.inggameteam.scheduler.repeat
import org.bukkit.event.EventHandler

interface Broadcast : Game {

    @Deprecated("EventHandler")
    @EventHandler
    fun beginGame(event: GameBeginEvent) {
        if (this !== event.game) return
        var timer = 0
        addTask({
            comp.send("broadcast-$timer", joined, timer)
            timer++
            true
        }.repeat(plugin, 20, 20))
    }

}