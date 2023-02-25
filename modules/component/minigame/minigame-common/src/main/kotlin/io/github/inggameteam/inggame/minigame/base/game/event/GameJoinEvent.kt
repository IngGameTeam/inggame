package io.github.inggameteam.inggame.minigame.base.game.event

import io.github.inggameteam.inggame.minigame.base.game.Game
import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class GameJoinEvent(val game: Game, val player: GPlayer) : Event() {
    override fun getHandlers(): HandlerList { return HANDLERS }
    companion object {
        @JvmStatic
        val HANDLERS = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList { return HANDLERS }
    }
}