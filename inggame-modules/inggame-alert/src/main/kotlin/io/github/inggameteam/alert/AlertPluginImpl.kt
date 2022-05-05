package io.github.inggameteam.alert

import io.github.inggameteam.alert.component.Component
import io.github.inggameteam.alert.component.ComponentImpl
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

    override val defaultLanguage = "default"
    override val components = HashMap<String, Component>()
    override val component get() = components[DEFAULT_DIR]
        .apply { assertNotNull(this, "component $DEFAULT_DIR does not exist") }!!
    override fun onEnable() {
        super.onEnable()
        components
        dataFolder.listFiles(File::isDirectory)?.forEach { file ->
            components[file.name] = ComponentImpl(this, file)
        }
    }

}