package io.github.inggameteam.player

import io.github.inggameteam.alert.AlertPluginImpl
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPluginLoader
import java.io.File

open class PlayerPluginImpl : PlayerPlugin, AlertPluginImpl {
    constructor()
    constructor(loader: JavaPluginLoader, description: PluginDescriptionFile, dataFolder: File, file: File)
            : super(loader, description, dataFolder, file)

    override val playerRegister by lazy { GPlayerRegister(this) }
    override fun onEnable() {
        super.onEnable()
        playerRegister
    }
}