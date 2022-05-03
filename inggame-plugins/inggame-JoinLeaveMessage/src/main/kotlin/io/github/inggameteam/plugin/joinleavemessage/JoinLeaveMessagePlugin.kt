package io.github.inggameteam.plugin.joinleavemessage

import io.github.inggameteam.player.PlayerPluginImpl
import io.github.inggameteam.player.receiveAll
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class JoinLeaveMessagePlugin : PlayerPluginImpl() {

    fun String.broadcast(vararg args: String) =
        playerRegister.values.receiveAll(console, component.alert[this]!!, *args)

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) = "join".broadcast(event.player.name)

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) = "quit".broadcast(event.player.name)
}
