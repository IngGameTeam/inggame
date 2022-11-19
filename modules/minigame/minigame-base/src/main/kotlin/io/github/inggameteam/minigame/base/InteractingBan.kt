package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.GameState
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent

interface InteractingBan : Game {

    val noInteracts: List<Material>

    @Suppress("unused")
    @EventHandler
    fun staticBlock(event: BlockBreakEvent) = staticBreak(event.player, event.block.type, event)

    @Suppress("unused")
    @EventHandler
    fun staticBlock(event: BlockPlaceEvent) = staticBreak(event.player, event.itemInHand.type, event)

    @Suppress("unused")
    @EventHandler
    fun staticBlock(event: PlayerDropItemEvent) = staticBreak(event.player, event.itemDrop.itemStack.type, event)

    @Suppress("unused")
    @EventHandler
    fun staticInteractBlock(event: PlayerInteractEvent) {
        listOf(event.material).forEach { mat ->
            staticBreak(event.player, mat, event)
        }
    }

    @Suppress("unused")
    @EventHandler
    fun staticBlock(event: EntityPickupItemEvent) {
        if (event.entityType !== EntityType.PLAYER || gameState === GameState.WAIT) return
        val entity = event.entity
        staticBreak(entity as Player, event.item.itemStack.type, event)
    }

    fun staticBreak(player: Player, material: Material, event: Cancellable) {
        if (isJoined(player) && noInteracts.contains(material)) {
            event.isCancelled = true
        }
    }


}