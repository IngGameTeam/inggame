package io.github.inggameteam.inggame.minigame.base.game

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.minigame.base.game.event.GameJoinEvent
import io.github.inggameteam.inggame.minigame.base.player.PTag
import io.github.inggameteam.inggame.utils.*
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
            if (game.containerState === ContainerState.WAIT
                && game.hasGameTask()
                && game.joinedPlayers.hasTags(PTag.PLAY).size < game.startPlayersAmount
                && game.startPlayersAmount > 1
            ) {
                game.joinedPlayers.forEach {
                    it[::GameAlertImp].NEED_PLAYER.send(it, it[::GameImp].containerName, game.startPlayersAmount)
                }
            }
            game.addTask(ITask())
        }.delay(plugin, 0)
    }


}