package io.github.inggameteam

import io.github.inggameteam.alert.Component
import io.github.inggameteam.alert.AlertPlugin
import io.github.inggameteam.player.GPlayerList
import io.github.inggameteam.player.GPlayerRegister
import io.github.inggameteam.player.PlayerPlugin
import io.github.inggameteam.utils.randomUUID
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.JavaPluginLoader
import java.io.File
import java.util.*


class JoinLeaveMessagePlugin : JavaPlugin, AlertPlugin, PlayerPlugin, Listener {
    constructor()
    constructor(
        loader: JavaPluginLoader,
        description: PluginDescriptionFile,
        dataFolder: File,
        file: File,
    ) : super(loader, description, dataFolder, file)

    override lateinit var component: Component
    override lateinit var console: UUID
    override lateinit var playerRegister: GPlayerRegister

    override fun onEnable() {
        component = Component(dataFolder)
        console = randomUUID()
        playerRegister = GPlayerRegister(this)
    }

    fun String.broadcast(vararg args: String) =
        GPlayerList(playerRegister.values).receiveAll(console, component.alert[this]!!, *args)

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        println("joined")
        "join".broadcast(event.player.name)
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        println("quited")
        "quit".broadcast(event.player.name)
    }
}

