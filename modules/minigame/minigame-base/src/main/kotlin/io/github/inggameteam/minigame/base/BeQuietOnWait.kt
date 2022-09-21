package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.GameState
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerDropItemEvent

interface BeQuietOnWait : Game {

    @Suppress("unused")
    @EventHandler
    fun onBeQuietOnWaitItemDrop(event: PlayerDropItemEvent) = cancelIfWait(event.player, event)

    @Suppress("unused")
    @EventHandler
    fun onBeQuietOnWaitBlockPlace(event: BlockPlaceEvent) = cancelIfWait(event.player, event)

    @Suppress("unused")
    @EventHandler
    fun onBeQuietOnWaitBlockBreak(event: BlockBreakEvent) = cancelIfWait(event.player, event)

    @Suppress("unused")
    @EventHandler
    fun onBeQuietOnWaitFallDamage(event: EntityDamageEvent) {
        val player = event.entity
        if (event.cause === EntityDamageEvent.DamageCause.FALL && player is Player) {
            cancelIfWait(player, event)
        }
    }

    fun cancelIfWait(player: Player, event: Cancellable) {
        println("isJoined: ${isJoined(player)} && isGameStateWait: ${gameState === GameState.WAIT}")
        if (isJoined(player) && gameState === GameState.WAIT) {
            event.isCancelled = true
        }
    }

}