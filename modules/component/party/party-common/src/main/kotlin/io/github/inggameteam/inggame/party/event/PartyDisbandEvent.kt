package io.github.inggameteam.inggame.party.event

import io.github.inggameteam.inggame.party.wrapper.Party
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class PartyDisbandEvent(
    val party: Party,
) : Event() {
    override fun getHandlers(): HandlerList { return HANDLERS }
    companion object {
        @JvmStatic
        val HANDLERS = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList { return HANDLERS }
    }
}
