package io.github.inggameteam.inggame.plugin

import io.github.inggameteam.inggame.component.*
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.helper.AddToSaveRegistry
import io.github.inggameteam.inggame.component.view.ComponentViewModule
import io.github.inggameteam.inggame.minigame.GameModule
import io.github.inggameteam.inggame.player.PlayerModule
import io.github.inggameteam.inggame.utils.ClassUtil
import io.github.inggameteam.inggame.utils.IngGamePluginImp
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPluginLoader
import org.koin.core.Koin
import java.io.File

@Suppress("unused")
class Plugin : IngGamePluginImp {

    constructor()

    constructor(loader: JavaPluginLoader, description: PluginDescriptionFile, dataFolder: File, file: File)
            : super(loader, description, ClassUtil.getJarFile(Plugin::class.java).parentFile, file)

    private var appSemaphore = false
    private val appDelegate = lazy { loadApp(this) }
    val app: Koin
        get() = if (allowTask || !isEnabled) {
            if (appSemaphore) throw AssertionError("an error occurred while get app while initializing app")
            appSemaphore = true
            val result = appDelegate.getValue(this, ::app)
            appSemaphore = false
            result
        } else throw AssertionError("an error occurred while get app before initializing plugin")


    override fun onEnable() {
        super.onEnable()
        ComponentModule(this)
        PlayerModule(this)
        GameModule(this)
        ComponentViewModule(this)
        app
        load(app, File(dataFolder, "comps.yml"))
        debugCommand(this, app)
        app.getAll<ComponentService>().forEach { AddToSaveRegistry(it, this) }
        app.getAll<ComponentService>().map(ComponentService::layerPriority)
    }

    override fun onDisable() {
        super.onDisable()
        if (appDelegate.isInitialized()) {
            app.close()
        }
    }

}