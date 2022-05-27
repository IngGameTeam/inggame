package io.github.inggameteam.minigame.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.base.CompetitionImpl
import io.github.inggameteam.minigame.base.PreventFallDamage
import io.github.inggameteam.minigame.base.SimpleGame
import io.github.inggameteam.minigame.base.SpawnPlayer
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent

class Spleef(plugin: GamePlugin) : SimpleGame, CompetitionImpl(plugin), PreventFallDamage, SpawnPlayer {
    override val name get() = "spleef"

    @Suppress("unused")
    @EventHandler(priority = EventPriority.LOW)
    fun damage(event: EntityDamageByEntityEvent) {
        val attacker = event.damager
        val player = event.entity
        if (attacker is Player && player is Player && isJoined(player) && gameState === GameState.PLAY) {
            if (attacker.inventory.let { it.getItem(it.heldItemSlot)?.type === Material.DIAMOND_SHOVEL })
                event.damage = 1.0
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onBreakSnowBlock(event: BlockBreakEvent) {
        if (!isJoined(event.player)) return
        if (gameState === GameState.PLAY) {
            event.isDropItems = false
        }
    }

}