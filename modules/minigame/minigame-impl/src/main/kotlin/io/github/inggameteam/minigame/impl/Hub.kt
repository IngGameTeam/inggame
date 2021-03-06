package io.github.inggameteam.minigame.impl

import io.github.inggameteam.minigame.base.*
import io.github.inggameteam.minigame.base.Hub
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.JoinType
import io.github.inggameteam.minigame.event.GPlayerSpawnEvent
import io.github.inggameteam.minigame.event.GameJoinEvent
import io.github.inggameteam.player.GPlayer
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.Inventory

class Hub(plugin: GamePlugin) : Hub(plugin), SpawnPlayer, SpawnOnJoin, VoidDeath, SpawnHealth,
    ParticleOnSpawn, ClearPotionOnJoin, PreventFallDamage, NoBlockBreak, NoBlockPlace, NoItemDrop {

    @EventHandler
    override fun onJoinParticle(event: GPlayerSpawnEvent) {
        if (isInSector(event.player.location)) super.onJoinParticle(event)
    }

    override fun inventorySpawn(player: GPlayer, spawn: String): Inventory? {
        return super.inventorySpawn(player, if (player.isOp) "OP" else spawn)
    }

    override fun joinGame(gPlayer: GPlayer, joinType: JoinType): Boolean {
        return super.joinGame(gPlayer, joinType).apply {
            gPlayer.inventory.heldItemSlot = 0
        }
    }
}