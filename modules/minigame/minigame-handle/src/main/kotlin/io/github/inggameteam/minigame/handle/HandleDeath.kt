package io.github.inggameteam.minigame.handle

import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.event.GPlayerDeathEvent
import io.github.inggameteam.minigame.event.GPlayerSpawnEvent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageEvent

class HandleDeath(val plugin: GamePlugin) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun deathManager(event: EntityDamageEvent) {
        val player = event.entity
        if (!plugin.gameRegister.worldName.contains(player.world.name)) return
        if (player is Player) {
            if (plugin[player].hasTag(PTag.DEAD)) {
                event.isCancelled = true
                return
            }
            if (event.finalDamage >= player.health) {
                val deathEvent = GPlayerDeathEvent(plugin[player])
                Bukkit.getPluginManager().callEvent(deathEvent)
                event.isCancelled = true
                player.health = player.maxHealth
                player.fallDistance = 0f
                player.fireTicks = 0
                if (deathEvent.isCancelled === true) return
                val spawnEvent = GPlayerSpawnEvent(plugin[player])
                Bukkit.getPluginManager().callEvent(spawnEvent)
            }
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onSpawn(event: GPlayerSpawnEvent) {
        event.player.fallDistance = 0f
    }
}