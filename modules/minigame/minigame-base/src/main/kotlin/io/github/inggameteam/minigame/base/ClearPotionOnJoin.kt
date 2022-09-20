package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.event.GameJoinEvent
import org.bukkit.event.EventHandler

interface ClearPotionOnJoin : Game {

    @Suppress("unused")
    @EventHandler
    fun onJoinClearPotion(event: GameJoinEvent) {
        if (event.join !== this) return
        val player = event.player
        player.activePotionEffects.forEach { player.removePotionEffect(it.type) }
    }

}