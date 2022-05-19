package io.github.inggameteam.minigame.handle

import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.minigame.GamePlugin
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.world.ChunkUnloadEvent

class ChunkHandler(val plugin: GamePlugin) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler(priority = EventPriority.MONITOR)
    fun onUnloadChunk(event: ChunkUnloadEvent) {
        if (!plugin.gameRegister.worldName.contains(event.world.name)) return
        event.isSaveChunk = false
        event.chunk.entities.filter { it.type !== EntityType.PLAYER }.forEach { if (it.isDead.not()) it.remove() }
    }

}