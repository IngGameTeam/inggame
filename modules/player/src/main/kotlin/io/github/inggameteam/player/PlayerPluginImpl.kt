package io.github.inggameteam.player

import io.github.inggameteam.api.IngGamePluginImpl
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPluginLoader
import java.io.File

open class PlayerPluginImpl : PlayerPlugin, IngGamePluginImpl {
    constructor()
    constructor(loader: JavaPluginLoader, description: PluginDescriptionFile, dataFolder: File, file: File)
            : super(loader, description, dataFolder, file)

    override val playerRegister by lazy { GPlayerRegister(this) }
    override fun onEnable() {
        super.onEnable()
        playerRegister
    }
}