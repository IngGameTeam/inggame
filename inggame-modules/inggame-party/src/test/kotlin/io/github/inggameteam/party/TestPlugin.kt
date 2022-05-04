package io.github.inggameteam.party

import io.github.inggameteam.utils.YamlUtil
import org.bukkit.configuration.file.YamlConfiguration
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
) : PartyPluginImpl(
    loader, description, dataFolder, file
) {


    override fun onEnable() {
        super.onEnable()
        try {
            saveResource("party/string/default.yml", true)
            println(YamlUtil.string(YamlConfiguration.loadConfiguration(File(dataFolder, "party/string/default.yml")), "enable_plugin"))
            println(config.getString("key"))
            partyComponent.string.comp("enable_plugin")
        } catch(e: Exception) {e.printStackTrace()}
        println("enabled")
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        try {
            println("joined")
        } catch (e: Exception) {
            logger.warning(e.stackTraceToString())
        }

    }
}