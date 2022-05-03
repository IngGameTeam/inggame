package io.github.inggameteam.api

import io.github.inggameteam.utils.randomUUID
import org.bukkit.Bukkit
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.JavaPluginLoader
import java.io.File

open class IngGamePluginImpl : IngGamePlugin, JavaPlugin {

    override val console = randomUUID()
    override var allowTask = false

    constructor()
    constructor(loader: JavaPluginLoader, description: PluginDescriptionFile, dataFolder: File, file: File)
            : super(loader, description, dataFolder, file)

    override fun onEnable() {
//        super.onEnable()
        Bukkit.getPluginManager().registerEvents(this, this)
        allowTask = true
        console
    }

    override fun onDisable() {
//        super.onDisable()
        allowTask = false
    }

}