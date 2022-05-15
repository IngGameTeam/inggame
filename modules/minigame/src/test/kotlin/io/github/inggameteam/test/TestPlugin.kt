package io.github.inggameteam.test

import io.github.inggameteam.minigame.GamePluginImpl
import io.github.inggameteam.base.Hub
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
    width = 300, height = 128,
    init = arrayOf(::Hub),
    loader, description, dataFolder, file
) {

}
