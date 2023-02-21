package io.github.inggameteam.inggame.minigame.base.sectional

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.component.Handler.Companion.isHandler
import io.github.inggameteam.inggame.minigame.base.game.GameState
import io.github.inggameteam.inggame.minigame.base.game.event.*
import io.github.inggameteam.inggame.minigame.base.gameserver.GameServer
import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import io.github.inggameteam.inggame.minigame.component.GameInstanceService
import io.github.inggameteam.inggame.minigame.component.GamePlayerService
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.async
import io.github.inggameteam.inggame.utils.event.IngGamePluginEnableEvent
import io.github.inggameteam.inggame.utils.runNow
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerMoveEvent
import kotlin.system.measureTimeMillis

class SectionalHandler(
    private val sectionalHelper: SectionalHelper,
    private val gameInstanceService: GameInstanceService,
    private val gamePlayerService: GamePlayerService,
    private val plugin: IngGamePlugin,
    private val sectorLoader: SectorLoader,
    private val gameServer: GameServer,
) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onIngGamePluginDisable(event: IngGamePluginEnableEvent) {
        plugin.addDisableEvent {
            gameInstanceService.getAll(::SectionalImp)
                .filter { it.isHandler(SectionalHandler::class) }
                .forEach { sectionalHelper.unloadSector(it) }
        }
    }

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
        }
    }

    @Suppress("unused")
    @EventHandler
    fun outSectionCheck(event: PlayerMoveEvent) {
        val bPlayer = event.player
        val player = gamePlayerService[bPlayer.uniqueId, ::GPlayer]
        measureTimeMillis {
            repeat(1000) {
                if (isHandler(player)) {
                    val sectional = player[::SectionalImp]
                    sectional.schematicName
                }
            }
        }.apply {
            Bukkit.broadcastMessage(this.toString())
        }
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
            game.sector = sectorLoader.newAllocatable(gameServer.gameWorld)
            game.initPoints()
        }
    }

    @Suppress("unused")
    @EventHandler(priority = EventPriority.LOWEST)
    fun onUnloadGame(event: GameUnloadEvent) {
        if (isNotHandler(event.game)) return
        val game = event.game[::SectionalImp]
        if (!game.unloadingSemaphore) return
        event.isCancelled = true
        if (!plugin.allowTask) return
        ;{
            game.unloadingSemaphore = true
            sectionalHelper.unloadSector(game)
            ;{ plugin.server.pluginManager.callEvent(GameUnloadEvent(game)) }.runNow(plugin)
        }.async(plugin)
    }

}
