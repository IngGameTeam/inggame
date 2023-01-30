package io.github.inggameteam.inggame.minigame.handler

import io.github.inggameteam.inggame.component.PropHandler
import io.github.inggameteam.inggame.component.delegate.get
import io.github.inggameteam.inggame.minigame.GamePlayerService
import io.github.inggameteam.inggame.minigame.wrapper.game.GameAlert
import io.github.inggameteam.inggame.minigame.wrapper.game.GameAlertImp
import io.github.inggameteam.inggame.minigame.wrapper.player.GPlayer
import io.github.inggameteam.inggame.utils.HandleListener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import kotlin.system.measureTimeMillis

@PropHandler
class PrintOnMove(
    private val gamePlayerService: GamePlayerService,
    plugin: IngGamePlugin
) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onMove(event: PlayerMoveEvent) {
        val player = gamePlayerService.get(event.player.uniqueId, ::GPlayer)
        player[::GameAlertImp].testHashMap.put("HELLO", "ALALALAL")
        if (gamePlayerService[player, javaClass.simpleName, Boolean::class]) {
            gamePlayerService.get(player, ::GameAlertImp).GAME_JOIN.send(player)
            event.player.sendMessage("PrintOnMove!!!")
        }
    }

}