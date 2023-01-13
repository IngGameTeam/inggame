package io.github.inggameteam.inggame.plugin

import io.github.inggameteam.command.MCCommand
import io.github.inggameteam.command.player
import io.github.inggameteam.inggame.component.*
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.view.nsSelector
import io.github.inggameteam.inggame.minigame.createGameHandlers
import io.github.inggameteam.inggame.minigame.createGameService
import io.github.inggameteam.inggame.minigame.createGameResource
import io.github.inggameteam.inggame.mongodb.createFileRepo
import io.github.inggameteam.inggame.mongodb.createMongoModule
import io.github.inggameteam.inggame.mongodb.createRepo
import io.github.inggameteam.inggame.player.createPlayerModule
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.IngGamePluginImp
import org.koin.core.Koin
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import java.io.File

@Suppress("unused")
@Deprecated("need no refs")
class Plugin : IngGamePluginImp() {

    private val app: Koin by lazy { koinApplication {
        val codec = config.getStringList("codec")
        listOfNotNull(
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
                    getString("player")?.run(::createPlayerModule),
                    getString("game-instance")?.run(::createGameService),
                    getString("game-resource")?.run(::createGameResource),
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
        load(app, File(dataFolder, "comps.yml"))
        MCCommand(this) {
            command("ing") {
                execute {
                    nsSelector(app, app.get<ComponentService>(named(args[0])), this@Plugin)
                        .openInventory(player)
                }
            }
        }
    }

    override fun onDisable() {
        super.onDisable()
        app.close()
    }

}