package io.github.inggameteam.minigame.handle

import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.event.GPlayerWinEvent
import io.github.inggameteam.mongodb.impl.GameStats
import org.bukkit.event.EventHandler

class LogGameStats(val plugin: GamePlugin, val gameStats: GameStats) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun log(event: GPlayerWinEvent) {
        gameStats.addWonAmount(event.game.displayName(plugin.defaultLanguage))
    }


}