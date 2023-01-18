package io.github.inggameteam.inggame.minigame.handler

import io.github.inggameteam.inggame.component.PropHandler
import io.github.inggameteam.inggame.component.Wrapper
import io.github.inggameteam.inggame.minigame.GamePlayerService
import io.github.inggameteam.inggame.utils.HandleListener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerMoveEvent

@PropHandler
class PrintOnMove(
    private val gamePlayerService: GamePlayerService,
    plugin: IngGamePlugin
) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onMove(event: PlayerMoveEvent) {
        val player = event.player.uniqueId
        if (gamePlayerService.has(player, javaClass.simpleName)) {
            event.player.sendMessage("PrintOnMove!!!")
        }
    }

}