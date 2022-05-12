package io.github.inggameteam.alert

import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPluginLoader
import java.io.File

class TestPlugin(
    loader: JavaPluginLoader,
    description: PluginDescriptionFile,
    dataFolder: File,
    file: File,
) : AlertPluginImpl(loader, description, dataFolder, file) {


    override fun onEnable() {
        super.onEnable()
        println(component.string.comp("key", "default"))
    }



}