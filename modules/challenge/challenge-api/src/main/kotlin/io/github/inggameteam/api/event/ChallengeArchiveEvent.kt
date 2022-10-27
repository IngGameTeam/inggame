package io.github.inggameteam.api.event

import io.github.inggameteam.player.GPlayer
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class ChallengeArchiveEvent(
    val player: GPlayer,
    val name: String,
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
