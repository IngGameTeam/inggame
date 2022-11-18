package io.github.inggameteam.minigame.handle.event

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.player.GPlayer
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class RewardPointEvent(val player: GPlayer, val game: Game, val amount: Int) : Event() {
    override fun getHandlers(): HandlerList { return HANDLERS }
    companion object {
        @JvmStatic
        val HANDLERS = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList { return HANDLERS }
    }

}
