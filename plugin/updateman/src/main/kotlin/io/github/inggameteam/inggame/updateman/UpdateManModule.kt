package io.github.inggameteam.inggame.updateman

import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.Listener
import io.github.inggameteam.inggame.utils.event.IngGamePluginEnableEvent
import org.bukkit.event.EventHandler

class UpdateManModule(plugin: IngGamePlugin) : Listener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onUpdateMan(event: IngGamePluginEnableEvent) {
        val plugin = event.plugin
        val config = plugin.config
        val settings = config.run {
            UpdateSettings(
                plugin.name,
                getString("gitUrl") ?: return,
                getString("branchName") ?: return,
                getString("outputPath") ?: return,
                getString("bashCmd") ?: return
            )
        }
        if (config.getBoolean("watchdog")) {
            val updateHelper = UpdateHelper()
            UpdateWatchDog(settings, updateHelper, plugin)
        }
    }

}