package io.github.inggameteam.inggame.plugin

import io.github.inggameteam.inggame.component.*
import io.github.inggameteam.inggame.minigame.*
import io.github.inggameteam.inggame.mongodb.createFileRepo
import io.github.inggameteam.inggame.mongodb.createMongoModule
import io.github.inggameteam.inggame.mongodb.createRepo
import io.github.inggameteam.inggame.player.createPlayerModule
import io.github.inggameteam.inggame.utils.ClassUtil.matchClass
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.koin.core.Koin
import org.koin.dsl.bind
import org.koin.dsl.koinApplication
import org.koin.dsl.module

fun loadApp(plugin: IngGamePlugin): Koin {
    return plugin.run {
        koinApplication {
            modules(module { single { plugin } bind IngGamePlugin::class })
            val codec = config.getStringList("codec")
            listOfNotNull(
                registerComponentProps(),
                registerGameProps(),
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
                createPriorityFactory(),
                *config.getConfigurationSection("layer")?.run {
                    getKeys(false).map { layer -> createLayer(layer, getString(layer)!!) }.toTypedArray()
                } ?: emptyArray(),
                *config.getConfigurationSection("resource")?.run {
                    getKeys(false).map { layer -> createResource(layer, getString(layer)!!) }.toTypedArray()
                } ?: emptyArray(),
                *config.getConfigurationSection("save")?.run {
                    getKeys(false).map { layer -> addToSaveRegistry(layer) }.toTypedArray()
                } ?: emptyArray(),
                createEmpty("default"),
                createGameHandlers(),
                *config.getConfigurationSection("service")?.run {
                    listOfNotNull(
                        getString("player")?.run(::createPlayerModule),
                        getString("game-instance")?.run(::createGameService),
                        getString("game-resource")?.run(::createGameResource),
                        getString("game-player")?.run(::createGamePlayerService),
                    ).toTypedArray()
                }?: emptyArray(),
                *config.getConfigurationSection("singleton")?.run {
                    getKeys(false).firstNotNullOfOrNull { component ->
                        getConfigurationSection(component)?.run {
                            getKeys(false).map { ns ->
                                val clazz = getString(ns)!!.run {
                                    matchClass(codec, this)
                                }
                                createSingleton(clazz, ns, component)
                            }
                        }
                    }?.toTypedArray()
                }?: emptyArray()
            ).apply { modules(this) }
        }.koin
    }
}