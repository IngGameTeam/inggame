package io.github.inggameteam.minigame.handle

import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.event.GameJoinEvent
import io.github.inggameteam.scheduler.delay
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority

class JoinHubJoinGame(val game: String, val plugin: GamePlugin) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onJoinGame(event: GameJoinEvent) {
        if (event.join.name == plugin.gameRegister.hubName) {
            {
                plugin.gameRegister.join(event.player, game)
            }.delay(plugin, 10)
        }
    }


}