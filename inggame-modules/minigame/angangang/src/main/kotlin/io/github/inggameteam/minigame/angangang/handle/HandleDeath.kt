package io.github.inggameteam.minigame.angangang.handle

import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.event.GPlayerDeathEvent
import io.github.inggameteam.minigame.event.GPlayerSpawnEvent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageEvent

class HandleDeath(val plugin: GamePlugin) : HandleListener(plugin) {

    @Deprecated("EventHandler")
    @EventHandler
    fun deathManager(event: EntityDamageEvent) {
        val player = event.entity
        if (!plugin.gameRegister.worldName.contains(player.world.name)) return
        if (player is Player) {
            if (event.finalDamage >= player.health) {
                Bukkit.getPluginManager().callEvent(GPlayerDeathEvent(plugin[player]))
                event.isCancelled = true
                player.health = player.maxHealth
                player.fallDistance = 0f
                player.fireTicks = 0
                val spawnEvent = GPlayerSpawnEvent(plugin[player])
                Bukkit.getPluginManager().callEvent(spawnEvent)
            }
        }
    }

    @Deprecated("EventHandler")
    @EventHandler
    fun onSpawn(event: GPlayerSpawnEvent) {
        event.player.fallDistance = 0f
    }
}