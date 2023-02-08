package io.github.inggameteam.inggame.minigame.base.sectional

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.minigame.base.game.GameServer
import io.github.inggameteam.inggame.minigame.base.game.GameState
import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import io.github.inggameteam.inggame.minigame.component.GamePlayerService
import io.github.inggameteam.inggame.minigame.event.GameFinishEvent
import io.github.inggameteam.inggame.minigame.event.GameJoinEvent
import io.github.inggameteam.inggame.minigame.event.GameLeftEvent
import io.github.inggameteam.inggame.minigame.event.GameLoadEvent
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.delay
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerMoveEvent

class SectionalHandler(
    private val sectionalHelper: SectionalHelper,
    private val gamePlayerService: GamePlayerService,
    private val plugin: IngGamePlugin,
    private val sectorLoader: SectorLoader,
    private val gameServer: GameServer,
) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onJoinGame(event: GameJoinEvent) {
        val game = event.game[::SectionalImp]
        if (isNotHandler(game)) return
        if (game.isAllocatedGame && game.gameJoined.size == 1) {
            val sectional = game[::SectionalImp]
            sectionalHelper.loadSector(sectional)
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onLeftGame(event: GameLeftEvent) {
        val game = event.left[::SectionalImp]
        if (isNotHandler(game)) return
        if (game.isAllocatedGame && game.gameJoined.size == 0) {
            val sectional = game[::SectionalImp]
            sectionalHelper.clearEntitiesToUnload(sectional)
            ;{ sectionalHelper.unloadSector(sectional); }.delay(plugin, 20)
        }
    }

    @Suppress("unused")
    @EventHandler
    fun outSectionCheck(event: PlayerMoveEvent) {
        val bPlayer = event.player
        val player = gamePlayerService[bPlayer.uniqueId, ::GPlayer]
        if (isHandler(player)) {
            val sectional = player[::SectionalImp]
            val to = event.to
            if (to != null && !sectional.isInSector(to)
                && !bPlayer.isOp && sectional.gameState !== GameState.WAIT
            ) event.isCancelled = true
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onFinishGame(event: GameFinishEvent) {
        val game = event.game[::SectionalImp]
        if (isHandler(game)) {
            sectionalHelper.clearEntitiesToUnload(game)
        }
    }

    @Suppress("unused")
    @EventHandler(priority = EventPriority.LOW)
    fun onLoadGame(event: GameLoadEvent) {
        val game = event.game[::SectionalImp]
        if (isHandler(game)) {
            game.gameSector = sectorLoader.newAllocatable(gameServer.gameWorld)
            game.initPoints()
        }
    }

}