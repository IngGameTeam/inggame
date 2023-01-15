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

    private var appSemaphore = false
    private val appDelegate = lazy {
        if (appSemaphore) throw AssertionError("an error occurred while get app while initializing app")
        println("APP_LOAD")
        appSemaphore = true
        val result = loadApp(this)
        appSemaphore = false
        result
    }
    private val app: Koin by appDelegate


    override fun onEnable() {
        super.onEnable()
        println("HELLO")
        app
        load(app, File(dataFolder, "comps.yml"))
        debugCommand(this, app)
    }

    override fun onDisable() {
        super.onDisable()
        if (appDelegate.isInitialized()) {
            app.close()
        }
    }

}