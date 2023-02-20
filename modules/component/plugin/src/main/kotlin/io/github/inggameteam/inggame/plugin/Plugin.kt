package io.github.inggameteam.inggame.plugin

import io.github.inggameteam.inggame.component.*
import io.github.inggameteam.inggame.inggame.IngGame
import io.github.inggameteam.inggame.inggame.IngGamePluginImp
import io.github.inggameteam.inggame.minigame.GameModule
import io.github.inggameteam.inggame.minigame.view.GameViewModule
import io.github.inggameteam.inggame.utils.ClassUtil
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPluginLoader
import java.io.File

@Suppress("unused")
class Plugin : IngGamePluginImp {

    constructor()

    constructor(loader: JavaPluginLoader, description: PluginDescriptionFile, dataFolder: File, file: File)
            : super(loader, description, ClassUtil.getJarFile(Plugin::class.java).parentFile, file)


    override fun onEnable() {
        super.onEnable()
        debugCommand(this, ingGame.app)
    }

    override fun registerModule() {
        GameModule(this)
        GameViewModule(this)
    }

}