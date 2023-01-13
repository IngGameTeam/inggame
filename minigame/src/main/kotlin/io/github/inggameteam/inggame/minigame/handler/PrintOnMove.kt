package io.github.inggameteam.inggame.minigame.handler

import io.github.inggameteam.inggame.component.delegate.get
import io.github.inggameteam.inggame.minigame.wrapper.GameServer
import io.github.inggameteam.inggame.minigame.wrapper.player.GPlayer
import io.github.inggameteam.inggame.player.PlayerService
import io.github.inggameteam.inggame.utils.HandleListener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerMoveEvent
import kotlin.system.measureTimeMillis

class PrintOnMove(
    plugin: IngGamePlugin
) : HandleListener(plugin) {


    @Suppress("unused")
    @EventHandler
    fun onMove(event: PlayerMoveEvent) {
        println("HELLO")
        println(measureTimeMillis { repeat(500) { javaClass.simpleName } })
    }

}