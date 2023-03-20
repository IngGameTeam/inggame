package io.github.inggameteam.inggame.party.event

import io.github.inggameteam.inggame.party.wrapper.PartyPlayer
import io.github.inggameteam.inggame.player.wrapper.WrappedPlayer
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class CreatePartyEvent(
    val player: PartyPlayer
    ) : Event() {
    override fun getHandlers(): HandlerList { return HANDLERS }
    companion object {
        @JvmStatic
        val HANDLERS = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList { return HANDLERS }
    }
}
