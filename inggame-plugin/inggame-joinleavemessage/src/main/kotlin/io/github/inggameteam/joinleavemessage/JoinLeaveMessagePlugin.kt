package io.github.inggameteam.joinleavemessage

import io.github.inggameteam.alert.Component
import io.github.inggameteam.alert.AlertPlugin
import io.github.inggameteam.player.GPlayerList
import io.github.inggameteam.player.GPlayerRegister
import io.github.inggameteam.player.PlayerPlugin
import io.github.inggameteam.utils.randomUUID
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin

class JoinLeaveMessagePlugin : JavaPlugin(), Listener,
    AlertPlugin, PlayerPlugin {

    override val component by lazy { Component(dataFolder) }
    override val console = randomUUID()
    override val playerRegister by lazy { GPlayerRegister(this) }

    override fun onEnable() {
        component
        playerRegister
        Bukkit.getPluginManager().registerEvents(this, this)
    }

    fun String.broadcast(vararg args: String) =
        GPlayerList(playerRegister.values).receiveAll(console, component.alert[this]!!, *args)

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) = "join".broadcast(event.player.name)

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) = "quit".broadcast(event.player.name)
}
