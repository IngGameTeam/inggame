package io.github.inggameteam.minigame.event

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.LeftType
import io.github.inggameteam.player.GPlayer
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class GameLeftEvent(
    val player: GPlayer,
    val left: Game,
    val leftType: LeftType,
) : Event() {
    override fun getHandlers(): HandlerList { return HANDLERS }
    companion object {
        @JvmStatic
        val HANDLERS = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList { return HANDLERS }
    }
}
