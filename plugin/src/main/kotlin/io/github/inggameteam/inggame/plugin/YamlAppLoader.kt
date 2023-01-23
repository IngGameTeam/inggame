package io.github.inggameteam.inggame.plugin

import io.github.inggameteam.inggame.component.*
import io.github.inggameteam.inggame.minigame.*
import io.github.inggameteam.inggame.mongodb.createFileRepo
import io.github.inggameteam.inggame.mongodb.createModelRegistryAll
import io.github.inggameteam.inggame.mongodb.createMongoModule
import io.github.inggameteam.inggame.mongodb.createRepo
import io.github.inggameteam.inggame.player.createPlayerInstanceModule
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
                createSubClassRegistry(),
                createEditorRegistry(),
                createPropertyRegistry(),
                createModelRegistryAll(),
                registerComponentModels(),
                registerGameModels(),
                createEmpty("default"),
                createMongoModule(config.getString("url") ?: "unspecified"),
                *config.getConfigurationSection("repo")?.run {
                    getKeys(false).map { key -> createRepo(key, getString(key)!!) }.toTypedArray()
                } ?: emptyArray(),
                *config.getConfigurationSection("file")?.run {
                    getKeys(false).map { key -> createFileRepo(key, getString(key)!!) }.toTypedArray()
                } ?: emptyArray(),
                *config.getConfigurationSection("layer")?.run {
                    getKeys(false).map { key -> createLayer(key, getString(key)!!) }.toTypedArray()
                } ?: emptyArray(),
                *config.getConfigurationSection("multi-parents")?.run {
                    getKeys(false).map { key -> createMultiParents(key,
                        getString("$key.root")!!,
                        getStringList("$key.components"),
                        getString("$key.key")
                    ) }.toTypedArray()
                } ?: emptyArray(),
                *config.getConfigurationSection("resource")?.run {
                    getKeys(false).map { key -> createResource(key, getString(key)!!) }.toTypedArray()
                } ?: emptyArray(),
                *config.getConfigurationSection("save")?.run {
                    getKeys(false).map { key ->  ;addToSaveRegistry(key) }.toTypedArray()
                } ?: emptyArray(),
                createGameHandlers(),
                *config.getConfigurationSection("service")?.run {
                    listOfNotNull(
                        getString("player-instance")?.run(::createPlayerInstanceModule),
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