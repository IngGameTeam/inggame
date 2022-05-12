package io.github.inggameteam.minigame.event

import io.github.inggameteam.minigame.Game
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class GameUnloadEvent(val game: Game) : Event() {
    override fun getHandlers(): HandlerList { return HANDLERS }
    companion object {
        @JvmStatic
        val HANDLERS = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList { return HANDLERS }
    }
}