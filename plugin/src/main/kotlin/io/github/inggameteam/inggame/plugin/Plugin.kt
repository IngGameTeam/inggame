package io.github.inggameteam.inggame.plugin

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.ResourceComponentService
import io.github.inggameteam.inggame.component.createLayer
import io.github.inggameteam.inggame.component.createResource
import io.github.inggameteam.inggame.component.createSingleton
import io.github.inggameteam.inggame.component.delegate.Delegate
import io.github.inggameteam.inggame.component.delegate.NonNullDelegateImp
import io.github.inggameteam.inggame.component.delegate.SimpleDelegate
import io.github.inggameteam.inggame.minigame.createGameHandlers
import io.github.inggameteam.inggame.minigame.createGameService
import io.github.inggameteam.inggame.minigame.wrapper.Server
import io.github.inggameteam.inggame.mongodb.createMongoModule
import io.github.inggameteam.inggame.mongodb.createRepo
import io.github.inggameteam.inggame.player.createPlayerModule
import io.github.inggameteam.inggame.player.handler.PlayerLoader
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.IngGamePluginImp
import org.bukkit.plugin.Plugin
import org.koin.core.Koin
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.reflections.util.QueryFunction.single

class Plugin : IngGamePluginImp() {

    private val url = "mongodb+srv://Bruce0203:F8oP5Y8USXyUfmA5@cluster0.tnbppk8.mongodb.net/?retryWrites=true&w=majority"
    private val codecPackage = "io.github.inggameteam.inggame"
    private val database = "angangang"
    private val component = "component"
    private val player = "player"
    private val resource = "resource"
    private val game = "game"

    val app: Koin by lazy { koinApplication {
        modules(
            createGameHandlers(),
            module { single { this@Plugin } bind IngGamePlugin::class },
            module { single { this@Plugin } bind Plugin::class },
            createSingleton(::Server, "server", resource),
            createMongoModule(url, codecPackage, database),
            createRepo(component),
            createRepo(player),
            createResource(resource, component),
            createLayer(player, resource),
            createPlayerModule(player),
            createGameService(game),
        )
    }.koin }

    override fun onEnable() {
        super.onEnable()
    }

    override fun onDisable() {
        super.onDisable()
        app.close()
    }

}