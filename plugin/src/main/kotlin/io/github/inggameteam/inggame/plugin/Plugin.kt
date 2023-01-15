package io.github.inggameteam.inggame.plugin

import io.github.inggameteam.inggame.component.*
import io.github.inggameteam.inggame.utils.IngGamePluginImp
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPluginLoader
import org.koin.core.Koin
import java.io.File

@Suppress("unused")
class Plugin : IngGamePluginImp {

    constructor()
    constructor(loader: JavaPluginLoader, description: PluginDescriptionFile, dataFolder: File, file: File)
            : super(loader, description, dataFolder, file)

    private val app: Koin by lazy { loadApp(this) }

    override fun onEnable() {
        super.onEnable()
        app
        load(app, File(dataFolder, "comps.yml"))
        debugCommand(this, app)
    }

    override fun onDisable() {
        super.onDisable()
        app.close()
    }

}