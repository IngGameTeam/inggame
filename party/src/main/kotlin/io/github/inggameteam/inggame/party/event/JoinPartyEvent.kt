package io.github.inggameteam.inggame.party.event

import io.github.inggameteam.inggame.party.wrapper.Party
import io.github.inggameteam.inggame.party.wrapper.PartyPlayer
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class JoinPartyEvent(
    val player: PartyPlayer,
    val joined: Party,
) : Event() {
    override fun getHandlers(): HandlerList { return HANDLERS }
    companion object {
        @JvmStatic
        val HANDLERS = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList { return HANDLERS }
    }
}
