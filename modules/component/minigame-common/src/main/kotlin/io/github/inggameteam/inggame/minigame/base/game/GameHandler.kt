package io.github.inggameteam.inggame.minigame.base.game

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.minigame.base.game.event.GameUnloadEvent
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.runNow
import org.bukkit.event.EventHandler

class GameHandler(
    private val gameHelper: GameHelper,
    val plugin: IngGamePlugin
) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onUnloadGame(event: GameUnloadEvent) {
        println("GameUnloadHandler")
        if (isNotHandler(event.game)) return
        ;{
            gameHelper.removeGame(event.game)
        }.runNow(plugin)
    }

}