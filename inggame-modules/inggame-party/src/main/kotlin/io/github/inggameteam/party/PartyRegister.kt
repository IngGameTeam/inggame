package io.github.inggameteam.party

import io.github.inggameteam.party.event.CreatePartyEvent
import io.github.inggameteam.player.GPlayer
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*

class PartyRegister(private val partyPlugin: PartyPlugin) : HashSet<Party>(), Listener {

    @EventHandler(priority = EventPriority.LOW)
    fun left(event: PlayerQuitEvent) {
        val gPlayer = partyPlugin.playerRegister[event.player]
        if (joinedParty(gPlayer)) getJoined(gPlayer)?.left(gPlayer)
    }
    fun joinedParty(gPlayer: GPlayer) = any{it.joined.contains(gPlayer)}
    fun hasOwnParty(gPlayer: GPlayer) = any{it.joined.getOrNull(0) == gPlayer}
    fun getJoined(gPlayer: GPlayer) = firstOrNull { it.joined.contains(gPlayer) }
    fun createParty(gPlayer: GPlayer) {
        if (joinedParty(gPlayer)) getJoined(gPlayer)?.left(gPlayer)
        else add(PartyImpl(partyPlugin, Collections.singletonList(gPlayer).toTypedArray()))
        Bukkit.getPluginManager().callEvent(CreatePartyEvent(gPlayer))
    }
}