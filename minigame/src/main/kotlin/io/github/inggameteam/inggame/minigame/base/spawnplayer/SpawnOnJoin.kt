package io.github.inggameteam.inggame.minigame.base.spawnplayer

import io.github.inggameteam.inggame.component.Handler
import io.github.inggameteam.inggame.minigame.event.GPlayerSpawnEvent
import io.github.inggameteam.inggame.minigame.event.GameJoinEvent
import io.github.inggameteam.inggame.utils.HandleListener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority

class SpawnOnJoin(val plugin: IngGamePlugin) : Handler, HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onJoinGame(event: GameJoinEvent) {
        if (isNotHandler(event.game)) return
        plugin.server.pluginManager.callEvent(GPlayerSpawnEvent(event.player))
    }

}