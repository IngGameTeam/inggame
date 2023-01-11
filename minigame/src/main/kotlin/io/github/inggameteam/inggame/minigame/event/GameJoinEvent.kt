package io.github.inggameteam.inggame.minigame.event

import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import java.util.*

class GameJoinEvent(val game: UUID, val player: UUID) : Event() {
    override fun getHandlers(): HandlerList { return HANDLERS }
    companion object {
        @JvmStatic
        val HANDLERS = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList { return HANDLERS }
    }
}