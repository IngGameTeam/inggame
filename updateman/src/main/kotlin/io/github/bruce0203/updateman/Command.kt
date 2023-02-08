package io.github.bruce0203.updateman

import io.github.inggameteam.command.Root
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask
import java.io.File
import java.util.concurrent.ConcurrentHashMap

fun Root.updateManCmd(cmdName: String, plugin: Plugin): Unit = plugin.run {
    val semaphore = ConcurrentHashMap<String, Boolean>()
    then(cmdName) {
        config.getKeys(false).forEach { key ->
            if (config.isSet("$key.watchdog")) {
                var func: ((t: BukkitTask) -> Unit)? = null
                func = block@{ _ ->
                    if (semaphore[key] === null) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "updateman $key")
                    }
                    Bukkit.getScheduler().runTaskLater(plugin, func!!, 20)
                }
                Bukkit.getScheduler().runTaskLater(plugin, func, 20)
            }
            then(key) {
                execute {
                    if (source is Player) return@execute
                    val section = config.getConfigurationSection(key)!!
                    if (section.getBoolean("pull")) {
                        pull(
                            section.getString("destiny")!!
                        )
                    } else if (section.getBoolean("download")) {
                        download(
                            section.getString("plugin")!!,
                            section.getString("url")!!,
                            section.getString("destiny")!!,
                        )
                    } else {
                        Update(
                            key,
                            plugin,
                            section.getString("plugin")!!,
                            section.getString("url")!!,
                            File(dataFolder, section.getString("dir")?: key),
                            section.getString("cmd")!!,
                            section.getString("out")!!,
                            section.getString("branch")!!,
                        )
                    }
                }
            }

        }
    }
}


