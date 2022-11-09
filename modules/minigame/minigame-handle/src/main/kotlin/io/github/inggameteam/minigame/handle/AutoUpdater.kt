package io.github.inggameteam.minigame.handle

import com.rylinaux.plugman.util.PluginUtil
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.api.PluginHolder
import io.github.inggameteam.downloader.download
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.scheduler.ITask
import io.github.inggameteam.scheduler.delay
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.Plugin

class AutoUpdater(override val plugin: GamePlugin) : HandleListener(plugin), PluginHolder<Plugin> {

    private var autoUpdating = false
    private var autoUpdatingTask: ITask? = null

    @Suppress("unused")
    @EventHandler
    fun onJoinNoPlayer(event: PlayerJoinEvent) {
        if (autoUpdating) {
            autoUpdating = false
            autoUpdatingTask?.cancel()
            autoUpdatingTask = null
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onLeftNoPlayer(event: PlayerQuitEvent) {
        if (isEmptyOnline(event.player)) {
            autoUpdatingTask = {
                if (isEmptyOnline()) {
                    download(plugin)
                    PluginUtil.reload(plugin)
                }
            }.delay(plugin, 20 * 10L)
        }

    }

    private fun isEmptyOnline(except: Player? = null): Boolean {
        return if (except !== null)
            Bukkit.getOnlinePlayers()
                .none { it.uniqueId != except.uniqueId }
        else Bukkit.getOnlinePlayers().isEmpty()
    }

}