package io.github.inggameteam.inggame.utils
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin

open class HandleListener(plugin: Plugin) : Listener, Handler {

    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }
}