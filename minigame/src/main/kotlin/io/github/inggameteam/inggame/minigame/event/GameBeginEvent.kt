package io.github.inggameteam.inggame.minigame.event

import io.github.inggameteam.inggame.minigame.wrapper.game.Game
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class GameBeginEvent(val game: Game) : Event() {
    override fun getHandlers(): HandlerList { return HANDLERS }
    companion object {
        @JvmStatic
        val HANDLERS = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList { return HANDLERS }
    }

}
