package io.github.inggameteam.minigame.test

import io.github.inggameteam.minigame.GamePluginImpl
import io.github.inggameteam.minigame.base.Hub
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
) : GamePluginImpl(
    hubName = "hub", worldName = "customized_world",
    width = 300, height = 128,
    init = arrayOf(
        ::Hub,
    ),
    loader, description, dataFolder, file
) {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        try {
            gameRegister.join(event.player, gameRegister.hubName)
            println("joined")
        } catch (e: Exception) {
            logger.warning(e.stackTraceToString())
        }

    }
}
