package io.github.inggameteam.inggame.plugin

import io.github.inggameteam.inggame.component.createEmpty
import io.github.inggameteam.inggame.component.createLayer
import io.github.inggameteam.inggame.component.createResource
import io.github.inggameteam.inggame.component.createSingleton
import io.github.inggameteam.inggame.minigame.createGameHandlers
import io.github.inggameteam.inggame.minigame.createGameResourceService
import io.github.inggameteam.inggame.minigame.createGameService
import io.github.inggameteam.inggame.mongodb.createFileRepo
import io.github.inggameteam.inggame.mongodb.createMongoModule
import io.github.inggameteam.inggame.mongodb.createRepo
import io.github.inggameteam.inggame.player.createPlayerModule
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.IngGamePluginImp
import org.koin.core.Koin
import org.koin.dsl.bind
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.core.module.Module

@Suppress("unused")
@Deprecated("need no refs")
class Plugin : IngGamePluginImp() {

    private val app: Koin by lazy { koinApplication {
        val codec = config.getStringList("codec")
        val modules = listOfNotNull<Module>(
            createMongoModule(
                config.getString("url") ?: "unspecified",
                *codec.toTypedArray()
            ),
            *config.getConfigurationSection("repo")?.run {
                getKeys(false).map { repo -> createRepo(repo, getString(repo)!!) }.toTypedArray()
            } ?: emptyArray(),
            *config.getConfigurationSection("file")?.run {
                getKeys(false).map { repo -> createFileRepo(repo, getString(repo)!!) }.toTypedArray()
            } ?: emptyArray(),
            *config.getConfigurationSection("layer")?.run {
                getKeys(false).map { layer -> createLayer(layer, getString(layer)!!) }.toTypedArray()
            } ?: emptyArray(),
            *config.getConfigurationSection("resource")?.run {
                getKeys(false).map { layer -> createResource(layer, getString(layer)!!) }.toTypedArray()
            } ?: emptyArray(),
            createEmpty("default"),
            createGameHandlers(),
            *config.getConfigurationSection("service")?.run {
                listOfNotNull(
                    config.getString("player")?.run(::createPlayerModule),
                    config.getString("game")?.run(::createGameService),
                    config.getString("game-resource")?.run(::createGameResourceService),
                ).toTypedArray()
            }?: emptyArray(),
            *config.getConfigurationSection("singleton")?.run {
                getKeys(false).firstNotNullOfOrNull { component ->
                    getConfigurationSection(component)?.run {
                        getKeys(false).map { ns ->
                            val clazz = getString(ns)!!.run {
                                listOf(this, *codec.map { "$it.$this" }.toTypedArray()).firstNotNullOfOrNull {
                                    try {
                                        Class.forName(it).kotlin
                                    } catch (_: Exception) {
                                        null
                                    }
                                }
                                    ?: throw AssertionError("$this class not found")
                            }
                            createSingleton(clazz, ns, component)
                        }
                    }
                }?.toTypedArray()
            }?: emptyArray()
        ).apply { modules(this) }
        modules(
//            createSingleton(::GameServer, "server", resource),
            module { single { this@Plugin } bind IngGamePlugin::class },
        )
    }.koin }

    override fun onEnable() {
        super.onEnable()
        app
    }

    override fun onDisable() {
        super.onDisable()
        app.close()
    }

}