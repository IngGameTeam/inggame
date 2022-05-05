package io.github.inggameteam.alert

import be.seeseemelk.mockbukkit.entity.PlayerMock
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPluginLoader
import java.io.File

class TestPlugin(
    loader: JavaPluginLoader,
    description: PluginDescriptionFile,
    dataFolder: File,
    file: File,
) : AlertPluginImpl(loader, description, dataFolder, file) {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        try {
            println("asdfasdf")
            val player = event.player
            val gPlayer = this[player]
            component.send("test", gPlayer, "value")
            gPlayer.sendMessage("asdfasfdsfadafsdasfdfasdfasd")
            println((player as PlayerMock).nextMessage())
            println("asdfasdf")
        } catch(e: Exception) {e.printStackTrace()}
    }



}