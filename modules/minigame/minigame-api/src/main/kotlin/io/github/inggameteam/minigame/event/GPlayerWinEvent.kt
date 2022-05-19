package io.github.inggameteam.minigame.event

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.player.GPlayerList
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class GPlayerWinEvent(
    val game: Game,
    val player: GPlayerList
    ) : Event() {
    override fun getHandlers(): HandlerList { return HANDLERS }
    companion object {
        @JvmStatic
        val HANDLERS = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList { return HANDLERS }
    }

}