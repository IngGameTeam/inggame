package io.github.inggameteam.inggame.minigame.base

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.component.Handler.Companion.isHandler
import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import io.github.inggameteam.inggame.minigame.component.GamePlayerService
import io.github.inggameteam.inggame.minigame.event.GameJoinEvent
import io.github.inggameteam.inggame.minigame.event.GameLeftEvent
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.attribute.Attribute
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent

class EnableAttackSpeed(
    private val gamePlayerService: GamePlayerService,
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
    fun onGameJoin(event: PlayerJoinEvent) {
        disableAttackSpeed(gamePlayerService[event.player.uniqueId, ::GPlayer])
    }

    @Suppress("unused")
    @EventHandler
    fun onGameJoin(event: GameJoinEvent) {
        val game = event.game
        if (isHandler(game)) {
            enableAttackSpeed(event.player)
        } else {
            disableAttackSpeed(event.player)
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onGameLeft(event: GameLeftEvent) {
        val game = event.left
        if (isHandler(game)) {
            disableAttackSpeed(event.player)
        }
    }

}