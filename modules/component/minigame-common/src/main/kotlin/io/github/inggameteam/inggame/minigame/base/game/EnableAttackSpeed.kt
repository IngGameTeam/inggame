package io.github.inggameteam.inggame.minigame.base.game

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import io.github.inggameteam.inggame.minigame.base.game.event.GameJoinEvent
import io.github.inggameteam.inggame.minigame.base.game.event.GameLeftEvent
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.attribute.Attribute
import org.bukkit.event.EventHandler

class EnableAttackSpeed(
    val plugin: IngGamePlugin
) : HandleListener(plugin) {

    private fun disableAttackSpeed(player: GPlayer) {
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED)?.baseValue = 32.0
    }

    private fun enableAttackSpeed(player: GPlayer) {
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED)?.baseValue = 4.0

    }

    @Suppress("unused")
    @EventHandler
    fun onGameJoin(event: GameJoinEvent) {
        if (isHandler(event.player)) {
            disableAttackSpeed(event.player)
        } else {
            enableAttackSpeed(event.player)
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onGameLeft(event: GameLeftEvent) {
        val game = event.left
        if (isHandler(game)) {
            enableAttackSpeed(event.player)
        }
    }

}