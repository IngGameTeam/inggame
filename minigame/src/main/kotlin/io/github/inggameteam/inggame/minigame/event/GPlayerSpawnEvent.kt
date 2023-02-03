package io.github.inggameteam.inggame.minigame.event

import io.github.inggameteam.inggame.minigame.base.GPlayer
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class GPlayerSpawnEvent(
    val player: GPlayer,
    ) : Event() {
    override fun getHandlers(): HandlerList { return HANDLERS }
    companion object {
        @JvmStatic
        val HANDLERS = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList { return HANDLERS }
    }
}