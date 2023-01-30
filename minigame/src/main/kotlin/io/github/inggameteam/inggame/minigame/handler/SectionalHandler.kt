package io.github.inggameteam.inggame.minigame.handler

import io.github.inggameteam.inggame.component.delegate.get
import io.github.inggameteam.inggame.minigame.GamePlayerService
import io.github.inggameteam.inggame.minigame.GameState
import io.github.inggameteam.inggame.minigame.event.GameJoinEvent
import io.github.inggameteam.inggame.minigame.event.GameLeftEvent
import io.github.inggameteam.inggame.minigame.wrapper.game.SectionalImp
import io.github.inggameteam.inggame.utils.HandleListener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.delay
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerMoveEvent

class SectionalHandler(
    private val sectionalHelper: SectionalHelper,
    private val gamePlayerService: GamePlayerService,
    private val plugin: IngGamePlugin
) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onJoinGame(event: GameJoinEvent) {
        val game = event.game
        if (game.isAllocatedGame && game.gameJoined.size == 1) {
            val sectional = game[::SectionalImp]
            sectional.loadSector()
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onLeftGame(event: GameLeftEvent) {
        val game = event.left
        if (game.isAllocatedGame && game.gameJoined.size == 0) {
            val sectional = game[::SectionalImp]
            sectionalHelper.clearEntitiesToUnload(sectional)
            ;{ sectional.unloadSector(); }.delay(plugin, 20)
        }
    }

    @Suppress("unused")
    @EventHandler
    fun outSectionCheck(event: PlayerMoveEvent) {
        val bPlayer = event.player
        val player = bPlayer.uniqueId
        if (gamePlayerService.has(player, javaClass.simpleName)) {
            val sectional = gamePlayerService.get(player, ::SectionalImp)
            val to = event.to
            if (to != null && !sectional.isInSector(to)
                && !bPlayer.isOp && sectional.gameState !== GameState.WAIT) event.isCancelled = true
        }
    }

}