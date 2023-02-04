package io.github.inggameteam.inggame.plugin.test

import io.github.inggameteam.inggame.component.delegate.get
import io.github.inggameteam.inggame.minigame.GamePlayerService
import io.github.inggameteam.inggame.minigame.base.game.GameServer
import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import io.github.inggameteam.inggame.plugin.test.handler.joinPlayer
import io.github.inggameteam.inggame.plugin.test.handler.receiveMessage
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

@Test
class GameTest : Listener{

    init {
        println("GameTest!")
        val server = PLUGIN.app.get<GameServer>()
        println("server=${server.hub.nameSpace}")
        Bukkit.getPluginManager().registerEvents(this, PLUGIN)
        val player = joinPlayer("TestPlayer_1")
        player.simulatePlayerMove(player.location.clone().apply { x += 1 })
        val gPlayer = PLUGIN.app.get<GamePlayerService>()[player.uniqueId, ::GPlayer]
        PLUGIN.app.get<GamePlayerService>().find(player.uniqueId, "PrintOnMove", Any::class).apply { println("PrintOnMove=$this") }
        receiveMessage(player)

        SERVER.dispatchCommand(SERVER.consoleSender, "ing component player")
        SERVER.dispatchCommand(SERVER.consoleSender, "ing component game-player")
        SERVER.dispatchCommand(SERVER.consoleSender, "ing component singleton")
        SERVER.dispatchCommand(SERVER.consoleSender, "ing component game-instance")
        SERVER.dispatchCommand(player, "ing get game-player ${player.uniqueId} PrintOnMove")
        receiveMessage(player)
        println("GameTest end")
    }

    @EventHandler
    fun onMove(event: PlayerMoveEvent) {
        println("${event.player.name} moved!")
    }

}