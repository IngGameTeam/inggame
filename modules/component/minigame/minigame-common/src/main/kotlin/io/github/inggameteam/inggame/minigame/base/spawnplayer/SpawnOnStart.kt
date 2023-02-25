package io.github.inggameteam.inggame.minigame.base.spawnplayer

import io.github.inggameteam.inggame.component.Handler
import io.github.inggameteam.inggame.minigame.base.game.event.GPlayerSpawnEvent
import io.github.inggameteam.inggame.minigame.base.game.event.GameBeginEvent
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.Listener
import io.github.inggameteam.inggame.utils.fastForEach
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority

class SpawnOnStart(val plugin: IngGamePlugin) : Handler, Listener(plugin) {

    @Suppress("unused")
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onJoinGame(event: GameBeginEvent) {
        if (isNotHandler(event.game)) return
        event.game.containerJoined.toTypedArray().fastForEach {
            plugin.server.pluginManager.callEvent(GPlayerSpawnEvent(it))
        }
    }

}