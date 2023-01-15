package io.github.inggameteam.inggame.minigame.event

import io.github.inggameteam.inggame.minigame.wrapper.game.Game
import io.github.inggameteam.inggame.minigame.wrapper.player.GPlayer
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import java.util.*

class GameJoinEvent(val game: Game, val player: GPlayer) : Event() {
    override fun getHandlers(): HandlerList { return HANDLERS }
    companion object {
        @JvmStatic
        val HANDLERS = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList { return HANDLERS }
    }
}