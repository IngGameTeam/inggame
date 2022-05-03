package io.github.inggameteam.alert

import io.github.inggameteam.alert.component.Component
import io.github.inggameteam.alert.component.ComponentImpl
import io.github.inggameteam.api.IngGamePluginImpl
import io.github.inggameteam.player.PlayerPluginImpl
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPluginLoader
import java.io.File

open class AlertPluginImpl : AlertPlugin, PlayerPluginImpl {
    constructor()
    constructor(loader: JavaPluginLoader, description: PluginDescriptionFile, dataFolder: File, file: File)
            : super(loader, description, dataFolder, file)

    override val defaultLanguage = "default"
    override val components = HashMap<String, Component>()
    override val component by lazy { ComponentImpl(this, dataFolder) }
    override fun onEnable() {
        super.onEnable()
        components
        component
        dataFolder.listFiles { obj -> obj.isDirectory }?.forEach { file ->
            components[file.name] = ComponentImpl(this, file)
        }
    }
}