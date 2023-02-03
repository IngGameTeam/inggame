package io.github.inggameteam.inggame.minigame.base

import io.github.inggameteam.inggame.component.delegate.get
import io.github.inggameteam.inggame.minigame.GamePlayerService
import io.github.inggameteam.inggame.utils.HandleListener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerMoveEvent

class PrintOnMove(
    private val gamePlayerService: GamePlayerService,
    plugin: IngGamePlugin
) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onMove(event: PlayerMoveEvent) {
        val player = gamePlayerService.get(event.player.uniqueId, ::GPlayer)
        if (gamePlayerService.has(player, javaClass.simpleName)) {
            gamePlayerService.get(player, ::GameAlertImp).GAME_JOIN.send(player)
            event.player.sendMessage("PrintOnMove!!!")
        }
    }

}