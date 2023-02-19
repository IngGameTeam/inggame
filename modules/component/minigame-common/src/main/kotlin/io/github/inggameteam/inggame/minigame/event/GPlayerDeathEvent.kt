package io.github.inggameteam.inggame.minigame.event

import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class GPlayerDeathEvent(
    val player: GPlayer,
    val killer: Player? = player.killer,
    ) : Event(), Cancellable {
    override fun getHandlers(): HandlerList { return HANDLERS }
    companion object {
        @JvmStatic
        val HANDLERS = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList { return HANDLERS }
    }
    var cancel = false
    override fun isCancelled() = cancel
    override fun setCancelled(cancel: Boolean) { this.cancel = cancel }

}
