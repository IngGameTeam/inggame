package io.github.inggameteam.inggame.player.handler

import io.github.inggameteam.inggame.player.PlayerInstanceService
import io.github.inggameteam.inggame.utils.Helper
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*

@Helper
class PlayerLoader(private val playerInstanceService: PlayerInstanceService, private val plugin: IngGamePlugin) : Listener {

    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
        plugin.server.onlinePlayers.map(Player::getUniqueId).forEach(playerInstanceService::load)
    }

    @Suppress("unused")
    @EventHandler(priority = EventPriority.LOWEST)
    private fun onLogin(event: AsyncPlayerPreLoginEvent) {
        if (!plugin.allowTask || !plugin.isEnabled) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "please reconnect...")
            return
        }
        if (event.loginResult !== AsyncPlayerPreLoginEvent.Result.ALLOWED) return
        val uniqueId = event.uniqueId
        if (playerInstanceService.getOrNull(uniqueId) !== null)
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "committing your data... please reconnect.")
        else playerInstanceService.load(event.uniqueId)
    }

    @Suppress("unused")
    @EventHandler(priority = EventPriority.MONITOR)
    private fun onQuit(event: PlayerQuitEvent) {
        playerInstanceService.unload(event.player.uniqueId, true)
    }

    @Suppress("unused")
    @EventHandler(priority = EventPriority.MONITOR)
    private fun onKick(event: PlayerKickEvent) {
        playerInstanceService.unload(event.player.uniqueId, true)
    }

}