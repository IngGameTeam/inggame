package io.github.inggameteam.minigame

import io.github.inggameteam.utils.IntVector
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
    hubName = "hub",
    worldName = "customized_world",
    worldSize = IntVector(300, 128),
    init = arrayOf(
        ::Hub,
    ),
    loader, description, dataFolder, file
) {


    override fun onEnable() {
        super.onEnable()
        component.string["enable_plugin"]?.apply { logger.info(this) }
        println("enabled")
    }

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