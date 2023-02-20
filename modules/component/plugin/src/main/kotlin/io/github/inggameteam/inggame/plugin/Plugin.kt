package io.github.inggameteam.inggame.plugin

import io.github.inggameteam.inggame.component.*
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.loader.ComponentLoader
import io.github.inggameteam.inggame.component.view.ComponentViewModule
import io.github.inggameteam.inggame.inggame.IngGame
import io.github.inggameteam.inggame.inggame.IngGame.app
import io.github.inggameteam.inggame.inggame.IngGame.appDelegate
import io.github.inggameteam.inggame.item.ItemModule
import io.github.inggameteam.inggame.minigame.GameModule
import io.github.inggameteam.inggame.minigame.view.GameViewModule
import io.github.inggameteam.inggame.player.PlayerModule
import io.github.inggameteam.inggame.updateman.UpdateManModule
import io.github.inggameteam.inggame.utils.ClassUtil
import io.github.inggameteam.inggame.utils.Debug
import io.github.inggameteam.inggame.inggame.IngGamePluginImp
import io.github.inggameteam.inggame.utils.event.IngGamePluginEnableEvent
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPluginLoader
import org.koin.core.Koin
import java.io.File

@Suppress("unused")
class Plugin : IngGamePluginImp {

    constructor()

    constructor(loader: JavaPluginLoader, description: PluginDescriptionFile, dataFolder: File, file: File)
            : super(loader, description, ClassUtil.getJarFile(Plugin::class.java).parentFile, file)

    override fun registerModule() {
        GameModule(this)
        GameViewModule(this)
        debugCommand(this, app)
    }

}