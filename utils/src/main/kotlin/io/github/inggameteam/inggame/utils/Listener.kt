package io.github.inggameteam.inggame.utils
import org.bukkit.event.Listener

open class Listener(plugin: IngGamePlugin) : Listener {

    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }
}