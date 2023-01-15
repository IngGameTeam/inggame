package io.github.inggameteam.inggame.minigame.handler

import io.github.inggameteam.inggame.minigame.GameInstanceService
import io.github.inggameteam.inggame.minigame.GameResourceService
import io.github.inggameteam.inggame.minigame.singleton.GameServer
import io.github.inggameteam.inggame.player.PlayerService
import io.github.inggameteam.inggame.utils.HandleListener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerMoveEvent

class PrintOnMove(
    private val server: GameServer,
    private val playerService: PlayerService,
    private val gameResourceService: GameResourceService,
    private val gameInstanceService: GameInstanceService,
    plugin: IngGamePlugin
) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onMove(event: PlayerMoveEvent) {
        val player = event.player.uniqueId
        if (playerService.has(player, javaClass.simpleName)) {
            event.player.sendMessage("PrintOnMove!!!")
        }
    }

}