package io.github.inggameteam.api

import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin

open class HandleListener(plugin: Plugin) : Listener {

    init {
        Bukkit.getPluginManager().registerEvents(this, plugin)
    }
}