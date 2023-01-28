package io.github.inggameteam.inggame.minigame.handler

import io.github.inggameteam.inggame.component.PropHandler
import io.github.inggameteam.inggame.minigame.GamePlayerService
import io.github.inggameteam.inggame.player.PlayerService
import io.github.inggameteam.inggame.utils.HandleListener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerMoveEvent

@PropHandler
class PrintOnMove(
    private val gamePlayerService: PlayerService,
    plugin: IngGamePlugin
) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onMove(event: PlayerMoveEvent) {
        val player = event.player.uniqueId
        repeat(1000) {
            gamePlayerService[player, javaClass.simpleName, Boolean::class]
        }
        if (gamePlayerService[player, javaClass.simpleName, Boolean::class]) {
            event.player.sendMessage("PrintOnMove!!!")
        }
    }

}