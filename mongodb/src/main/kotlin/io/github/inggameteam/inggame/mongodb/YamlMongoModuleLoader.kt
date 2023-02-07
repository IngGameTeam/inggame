package io.github.inggameteam.inggame.mongodb

import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.koin.dsl.module

fun loadMongoModule(plugin: IngGamePlugin) = module {
    plugin.apply {
        listOfNotNull(
            createRegistryAll(),
            createMongoModule(config.getString("url") ?: "unspecified"),
            *config.getConfigurationSection("repo")?.run {
                getKeys(false).map { key -> createRepo(key, getString(key)!!) }.toTypedArray()
            } ?: emptyArray(),
            *config.getConfigurationSection("file")?.run {
                getKeys(false).map { key -> createFileRepo(key, getString(key)!!) }.toTypedArray()
            } ?: emptyArray(),
        ).apply { includes(this) }
    }
}