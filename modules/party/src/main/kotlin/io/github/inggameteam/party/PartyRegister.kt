package io.github.inggameteam.party

import io.github.inggameteam.api.PluginHolder
import io.github.inggameteam.player.GPlayer
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PartyRegister(override val plugin: PartyPlugin) : HashSet<Party>(), Listener, PluginHolder<PartyPlugin> {

    init { Bukkit.getPluginManager().registerEvents(this, plugin) }

    @EventHandler(priority = EventPriority.LOW)
    fun left(event: PlayerQuitEvent) {
        val gPlayer = plugin[event.player]
        if (joinedParty(gPlayer)) getJoined(gPlayer)?.left(gPlayer)
    }
    fun joinedParty(gPlayer: GPlayer) = any{it.joined.contains(gPlayer)}
    fun hasOwnParty(gPlayer: GPlayer) = any{it.joined.getOrNull(0) == gPlayer}
    fun getJoined(gPlayer: GPlayer) = firstOrNull { it.joined.contains(gPlayer) }
}