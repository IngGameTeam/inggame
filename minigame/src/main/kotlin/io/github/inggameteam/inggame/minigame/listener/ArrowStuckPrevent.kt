package io.github.inggameteam.inggame.minigame.listener

import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.Listener
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.ProjectileHitEvent

class ArrowStuckPrevent(plugin: IngGamePlugin) : Listener(plugin) {

    @Suppress("unused")
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onProjectileHit(event: ProjectileHitEvent) {
        event.entity.apply { eject() }
    }
}