package io.github.inggameteam.minigame.base

import io.github.inggameteam.bossbar.GBar
import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.event.GameJoinEvent
import io.github.inggameteam.minigame.event.GameLeftEvent
import org.bukkit.event.EventHandler

interface BarGame : Game {

    val bar: GBar

    @Suppress("unused")
    @EventHandler
    fun onJoinOrderBar(event: GameJoinEvent) {
        val gPlayer = plugin[event.player]
        if (!isJoined(gPlayer)) return
        bar.addPlayer(gPlayer)
    }

    @Suppress("unused")
    @EventHandler
    fun onLeftOrderBar(event: GameLeftEvent) {
        val gPlayer = plugin[event.player]
        if (!isJoined(gPlayer)) return
        bar.removePlayer(gPlayer)
    }



}