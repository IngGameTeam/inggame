package io.github.inggameteam.minigame.handle

import io.github.inggameteam.api.HandleListener
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.plugin.Plugin

class ArrowStuckPreventHandler(plugin: Plugin) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onProjectileHit(event: ProjectileHitEvent) {
        event.entity.remove()
    }
}