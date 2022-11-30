package io.github.inggameteam.minigame.handle

import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.base.EnableAttackSpeed
import io.github.inggameteam.minigame.event.GameJoinEvent
import io.github.inggameteam.minigame.event.GameLeftEvent
import io.github.inggameteam.player.GPlayer
import org.bukkit.attribute.Attribute
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent

class AttackSpeed(val plugin: GamePlugin) : HandleListener(plugin) {

    companion object {
        const val ATTACK_SPEED_ENABLED = "attackSpeedEnabled"
    }

    private fun disableAttackSpeed(player: GPlayer) {
        player.remove(ATTACK_SPEED_ENABLED)
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED)?.baseValue = 32.0
    }

    private fun enableAttackSpeed(player: GPlayer) {
        player[ATTACK_SPEED_ENABLED] = true
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED)?.baseValue = 4.0

    }

    @Suppress("unused")
    @EventHandler
    fun onGameJoin(event: PlayerJoinEvent) {
        disableAttackSpeed(plugin[event.player])
    }

    @Suppress("unused")
    @EventHandler
    fun onGameJoin(event: GameJoinEvent) {
        val game = event.join
        if (game is EnableAttackSpeed) {
            enableAttackSpeed(event.player)
        } else {
            disableAttackSpeed(event.player)
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onGameLeft(event: GameLeftEvent) {
        val game = event.left
        if (game is EnableAttackSpeed) {
            disableAttackSpeed(event.player)
        }
    }

}