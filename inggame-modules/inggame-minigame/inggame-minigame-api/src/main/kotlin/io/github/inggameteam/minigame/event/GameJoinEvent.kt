package io.github.inggameteam.minigame.event

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.JoinType
import io.github.inggameteam.player.GPlayer
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class GameJoinEvent(
    val player: GPlayer,
    val join: Game,
    val joinType: JoinType,
    ) : Event() {
    override fun getHandlers(): HandlerList { return HANDLERS }
    companion object {
        @JvmStatic
        val HANDLERS = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList { return HANDLERS }
    }
}
