package io.github.inggameteam.inggame.minigame.base.game.event

import io.github.inggameteam.inggame.minigame.base.game.Game
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class GameUnloadEvent(val game: Game) : Event(), Cancellable {

    private var cancelled = false
    override fun isCancelled() = cancelled
    override fun setCancelled(cancel: Boolean) { cancelled = cancel }

    override fun getHandlers(): HandlerList { return HANDLERS }
    companion object {
        @JvmStatic
        val HANDLERS = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList { return HANDLERS }
    }
}