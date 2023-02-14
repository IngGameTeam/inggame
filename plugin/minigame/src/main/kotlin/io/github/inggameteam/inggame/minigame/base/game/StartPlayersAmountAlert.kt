package io.github.inggameteam.inggame.minigame.base.game

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.minigame.base.player.PTag
import io.github.inggameteam.inggame.minigame.event.GameJoinEvent
import io.github.inggameteam.inggame.utils.ITask
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.delay
import io.github.inggameteam.inggame.utils.hasTags
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority

class StartPlayersAmountAlert(val plugin: IngGamePlugin) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler(priority = EventPriority.HIGH)
    fun startPlayersAmountAlert(event: GameJoinEvent) {
        val player = event.player
        val game = event.game
        if (isNotHandler(player)) return
        {
            if (game.gameState === GameState.WAIT
                && game.hasGameTask()
                && game.gameJoined.hasTags(PTag.PLAY).size < game.startPlayersAmount
                && game.startPlayersAmount > 1
            ) {
                game.gameJoined.forEach {
                    it[::GameAlertImp].NEED_PLAYER.send(it, it[::GameImp].gameName, game.startPlayersAmount)
                }
            }
            game.addTask(ITask())
        }.delay(plugin, 0)
    }


}