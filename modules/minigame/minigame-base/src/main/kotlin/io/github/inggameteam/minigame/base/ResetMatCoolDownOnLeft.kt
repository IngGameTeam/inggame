package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.event.GameLeftEvent
import org.bukkit.Material
import org.bukkit.event.EventHandler

interface ResetMatCoolDownOnLeft : Game {

    val coolMats: List<Material>

    @Suppress("unused")
    @EventHandler
    fun gameLeft(event: GameLeftEvent) {
        if (event.left === this) {
            event.player.player.apply { coolMats.forEach { this?.setCooldown(it, 0) } }
        }
    }

}
