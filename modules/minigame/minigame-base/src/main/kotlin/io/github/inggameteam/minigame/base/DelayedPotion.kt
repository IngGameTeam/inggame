package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.event.GameBeginEvent
import io.github.inggameteam.player.hasTags
import io.github.inggameteam.scheduler.delay
import org.bukkit.event.EventHandler
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

interface DelayedPotion : Game {

    val potionEffect: PotionEffect get() = PotionEffect(PotionEffectType.GLOWING, 55555, 1)

    @Suppress("unused")
    @EventHandler
    fun delayedPotion(event: GameBeginEvent) {
        if (event.game !== this) return
        addTask({
            joined.hasTags(PTag.PLAY).forEach {
                it.addPotionEffect(potionEffect)
            }
        }.delay(plugin, 20 * 60 * 8/*8minute!!*/))
    }

}