package io.github.bruce0203.updateman

import io.github.inggameteam.command.MCCommand
import io.github.inggameteam.inggame.plugman.util.PluginUtil
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask
import java.io.File
import java.util.concurrent.ConcurrentHashMap

@Suppress("unused")
class Plugin : JavaPlugin() {

    val semaphore = ConcurrentHashMap<String, Boolean>()

    override fun onEnable() {
        MCCommand(this) {
            command("updateman") {
                then("reload") {
                    tab { this@Plugin.server.pluginManager.plugins.map { it.name } }
                    execute {
                        PluginUtil.reload((server.pluginManager.getPlugin(args[0])))
                    }
                }
                config.getKeys(false).forEach { key ->
                    if (config.isSet("$key.watchdog")) {
                        var func: ((t: BukkitTask) -> Unit)? = null
                        func = block@{ _ ->
                            if (semaphore[key] === null) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "updateman $key")
                            }
                            Bukkit.getScheduler().runTaskLater(this@Plugin, func!!, 20)
                        }
                        Bukkit.getScheduler().runTaskLater(this@Plugin, func, 20)
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
                                    this@Plugin,
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
    }


}