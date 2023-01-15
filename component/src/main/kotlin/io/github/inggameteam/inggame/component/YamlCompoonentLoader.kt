package io.github.inggameteam.inggame.component

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.utils.fastUUID
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
                        val componentService = app.get<ComponentService>(named(component))
                        if (key == "parents") {
                            try {
                                componentService.setParents(nameSpace, getStringList(key).map {
                                    try { it.fastUUID() } catch (_: Throwable) { it }
                                })
                            } catch (_: Throwable) {}
                        }  else {
                            val value = get(key)
                            componentService.set(nameSpace, key, value)
                        }
                    }
                }
            }
        }
    }
}