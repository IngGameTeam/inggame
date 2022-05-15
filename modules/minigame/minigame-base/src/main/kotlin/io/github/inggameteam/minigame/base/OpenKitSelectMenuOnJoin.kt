package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.event.GameJoinEvent
import org.bukkit.event.EventHandler

interface OpenKitSelectMenuOnJoin : KitSelectMenu {

    @Suppress("unused")
    @EventHandler
    fun onJoin(event: GameJoinEvent) {
        val player = event.player
        if (!isJoined(player)) return
        open(player)
    }

}