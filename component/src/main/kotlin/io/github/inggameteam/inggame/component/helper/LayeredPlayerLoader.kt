package io.github.inggameteam.inggame.component.helper

import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService
import io.github.inggameteam.inggame.utils.Listener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent

open class LayeredPlayerLoader(
    private val layeredComponentService: LayeredComponentService,
    plugin: IngGamePlugin
) : Listener(plugin) {

    init {
        plugin.server.onlinePlayers.map(Player::getUniqueId).forEach { layeredComponentService.load(it, true) }
    }

    @Suppress("unused")
    @EventHandler(priority = EventPriority.LOW)
    fun onJoin(event: PlayerJoinEvent) {
        layeredComponentService.load(event.player.uniqueId, true)
    }


    @Suppress("unused")
    @EventHandler(priority = EventPriority.MONITOR)
    fun onQuit(event: PlayerQuitEvent) {
        layeredComponentService.unload(event.player.uniqueId, false)
    }

    @Suppress("unused")
    @EventHandler(priority = EventPriority.MONITOR)
    fun onKick(event: PlayerKickEvent) {
        layeredComponentService.unload(event.player.uniqueId, false)
    }


}