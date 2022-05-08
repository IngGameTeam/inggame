package io.github.inggameteam.party.event

import io.github.inggameteam.player.GPlayer
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class CreatePartyEvent(
    val player: GPlayer
    ) : Event() {
    override fun getHandlers(): HandlerList { return HANDLERS }
    companion object {
        @JvmStatic
        val HANDLERS = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList { return HANDLERS }
    }
}
