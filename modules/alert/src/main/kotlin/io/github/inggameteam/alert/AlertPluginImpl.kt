package io.github.inggameteam.alert

import io.github.inggameteam.player.PlayerPluginImpl
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPluginLoader
import java.io.File
import kotlin.test.assertNotNull

const val DEFAULT_DIR = "default"

open class AlertPluginImpl : AlertPlugin, PlayerPluginImpl {
    constructor()
    constructor(loader: JavaPluginLoader, description: PluginDescriptionFile, dataFolder: File, file: File)
            : super(loader, description, dataFolder, file)

    override val defaultLanguage by lazy { config.getString("default-language")?: "default" }
    override val components by lazy { Components(this) }
    override val component get() = components[DEFAULT_DIR]
        .apply { assertNotNull(this, "component $DEFAULT_DIR does not exist") }
    override fun onEnable() {
        super.onEnable()
        components
    }

}