package io.github.inggameteam.plugin.joinleavemessage

import io.github.inggameteam.alert.AlertPluginImpl
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class JoinLeaveMessagePlugin : AlertPluginImpl() {

    fun String.broadcast(vararg args: String) =
        component.send(this, playerRegister.values, *args)

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) = "join".broadcast(event.player.name)

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) = "quit".broadcast(event.player.name)
}
