package io.github.inggameteam.inggame.minigame.base.gameserver

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class HideJoinLeaveMessage(
    private val gameServer: GameServer,
    plugin: IngGamePlugin
) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        if (isNotHandler(gameServer)) return
        event.joinMessage = null
    }

    @Suppress("unused")
    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        if (isNotHandler(gameServer)) return
        event.quitMessage = null
    }

}