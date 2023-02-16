package io.github.inggameteam.inggame.minigame.base.death

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.minigame.base.gameserver.GameServer
import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import io.github.inggameteam.inggame.minigame.base.player.PTag
import io.github.inggameteam.inggame.minigame.component.GamePlayerService
import io.github.inggameteam.inggame.minigame.event.GPlayerDeathEvent
import io.github.inggameteam.inggame.minigame.event.GPlayerSpawnEvent
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDamageEvent

class DeathHandler(
    private val gameServer: GameServer,
    private val gamePlayerService: GamePlayerService,
    private val plugin: IngGamePlugin
) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler(priority = EventPriority.MONITOR)
    fun onDeath(event: EntityDamageEvent) {
        if (event.entityType !== EntityType.PLAYER) return
        val bPlayer = event.entity as Player
        val player = gamePlayerService[bPlayer.uniqueId, ::GPlayer]
        if (isNotHandler(player)) return
        if (gameServer.gameWorld != player.world.name) return
        if (player.hasTag(PTag.DEAD)) {
            event.isCancelled = true
        }
        if (event.finalDamage >= player.health) {
            val deathEvent = GPlayerDeathEvent(player, player.killer)
            plugin.server.pluginManager.callEvent(deathEvent)
            event.isCancelled = true
            player.resetMaxHealth()
            player.health = player.maxHealth
            player.fallDistance = 0f
            player.fireTicks = 0
            if (deathEvent.isCancelled) return
            val spawnEvent = GPlayerSpawnEvent(player)
            plugin.server.pluginManager.callEvent(spawnEvent)
        }

    }


}