package io.github.inggameteam.inggame.minigame.base.game

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.minigame.event.GameBeginEvent
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.event.EventHandler

class AnnounceGameBegin(plugin: IngGamePlugin) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onBeginGame(event: GameBeginEvent) {
        val game = event.game
        if (isNotHandler(game)) return
        game.gameJoined.forEach { it[::GameAlertImp].GAME_START.send(it, it[::GameImp].gameName)}
    }

}