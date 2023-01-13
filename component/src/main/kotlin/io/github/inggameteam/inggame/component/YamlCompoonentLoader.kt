package io.github.inggameteam.inggame.component

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import org.bukkit.configuration.file.YamlConfiguration
import org.koin.core.Koin
import org.koin.core.qualifier.named
import java.io.File

fun load(app: Koin, file: File) {
    if (!file.exists()) return
    val yaml = YamlConfiguration.loadConfiguration(file)
    yaml.getKeys(false).forEach { component ->
        yaml.getConfigurationSection(component)?.run {
            getKeys(false).forEach { nameSpace ->
                getConfigurationSection(nameSpace)?.run {
                    getKeys(false).forEach { key ->
                        val value = get(key)
                        app.get<ComponentService>(named(component)).set(nameSpace, key, value)
                    }
                }
            }
        }
    }
}