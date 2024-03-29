package io.github.inggameteam.party

import io.github.inggameteam.api.PluginHolder
import io.github.inggameteam.player.GPlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PartyRequestRegister(override val plugin: PartyPlugin) : HashSet<PartyRequest>(), Listener,
    PluginHolder<PartyPlugin> {

    @EventHandler(priority = EventPriority.LOWEST)
    fun left(event: PlayerQuitEvent) {
        val player = plugin[event.player]
        removeRequest(player)
    }

    fun removeRequest(player: GPlayer) {
        removeIf { it.receiver == player || it.sender == player }
    }

}
