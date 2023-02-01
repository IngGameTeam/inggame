package io.github.inggameteam.inggame.minigame.handler

import io.github.inggameteam.inggame.component.delegate.get
import io.github.inggameteam.inggame.minigame.*
import io.github.inggameteam.inggame.minigame.event.*
import io.github.inggameteam.inggame.minigame.singleton.GameServer
import io.github.inggameteam.inggame.minigame.wrapper.game.Game
import io.github.inggameteam.inggame.minigame.wrapper.game.GameAlertImp
import io.github.inggameteam.inggame.minigame.wrapper.game.GameImp
import io.github.inggameteam.inggame.minigame.wrapper.player.GPlayer
import io.github.inggameteam.inggame.utils.ITask
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.delay
import io.github.inggameteam.inggame.utils.hasTags
import org.bukkit.Bukkit
import org.bukkit.Particle

class GameHelper(
    private val gameInstanceService: GameInstanceService,
    private val gamePlayerService: GamePlayerService,
    private val server: GameServer,
    private val plugin: IngGamePlugin
) {

    fun requestJoin(requestedGame: Game, player: GPlayer, joinType: JoinType, sendMessage: Boolean): Boolean {
        val gameAlert = (if (player.joinedGame !== null) player else requestedGame)[::GameAlertImp]
        if (requestedGame.gameJoined.contains(player)) {
            if (sendMessage) gameAlert.GAME_ALREADY_JOINED.send(player, requestedGame.gameName)
        } else if (requestedGame.gameState !== GameState.WAIT && joinType === JoinType.PLAY) {
            if (sendMessage) gameAlert.GAME_CANNOT_JOIN_DUE_TO_STARTED.send(player, requestedGame.gameName)
        } else if (requestedGame.playerLimitAmount > 0
            && requestedGame.gameJoined.hasTags(PTag.PLAY).size >= requestedGame.playerLimitAmount
            && joinType === JoinType.PLAY) {
            if (sendMessage) gameAlert.GAME_CANNOT_JOIN_PLAYER_LIMITED.send(player, requestedGame.gameName)
        } else {
            return true
        }
        return false
    }

    fun joinGame(game: Game, player: GPlayer, joinType: JoinType): Boolean {
        val gameAlert = player[::GameAlertImp]
        if (requestJoin(game, player, joinType, true)) {
            gameInstanceService.join(game, player)
            gameAlert.GAME_JOIN.send(player, game.gameName)
            if (joinType === JoinType.PLAY) player.addTag(PTag.PLAY)
            else gameAlert.GAME_START_SPECTATING.send(player, game.gameName)
            Bukkit.getPluginManager().callEvent(GameJoinEvent(game, player))

            if (!game.hasGameTask()
                && game.gameState === GameState.WAIT
                && 0 < game.startPlayersAmount
                && game.gameJoined.hasTags(PTag.PLAY).size >= game.startPlayersAmount)
                start(game, false)
            return true
        }
        return false
    }

    private fun requestLeft(game: Game, gPlayer: GPlayer, leftType: LeftType) = game.gameJoined.contains(gPlayer)

    fun leftGame(gPlayer: GPlayer, leftType: LeftType): Boolean {
        val game = gPlayer.joinedGame?: return false
        if (!requestLeft(game, gPlayer, leftType)) return false
        Bukkit.getPluginManager().callEvent(GameLeftEvent(gPlayer, game, leftType))

        val gameAlert = gPlayer[::GameAlertImp]
        if (leftType === LeftType.LEFT_SERVER) {
            gameAlert.GAME_LEFT_GAME_DUE_TO_SERVER_LEFT.send(gPlayer, game.gameName)
        } else {
            gameAlert.GAME_LEFT.send(gPlayer, game.gameName)
        }
        gPlayer.clearTags()
        gameInstanceService.left(gPlayer)
        val joinedSize = game.gameJoined.hasTags(PTag.PLAY).size
        if (game.gameState === GameState.WAIT
            && joinedSize < game.startPlayersAmount && game.hasGameTask()) {
            game.gameJoined.forEach { it[::GameAlertImp].GAME_START_CANCELLED_DUE_TO_PLAYERLESS.send(it) }
            game.cancelGameTask()
        }
        if (leftType.isJoinHub) {
            gameInstanceService.join(server.hub, gPlayer)
        }
        if (game.gameState != GameState.STOP && joinedSize <= if (game.gameState === GameState.PLAY) 1 else 0) {
            stop(game, false)
        }
        return true
    }

    fun start(game: Game, force: Boolean) {
        if (force) {
            game.gameState = GameState.PLAY
            afterParticle(game)
            beginGame(game)
            Bukkit.getPluginManager().callEvent(GameBeginEvent(game))
        } else if (game.gameState === GameState.WAIT) {
            val tick = game.startWaitingSecond
            if (tick < 0) {
                start(game, true)
            } else {
                val list = ArrayList<() -> Unit>()
                for (i in tick downTo  1) list.add {
                    game.gameJoined.forEach {
                        it[::GameAlertImp].GAME_START_COUNT_DOWN.send(it, it[::GameImp].gameName, i, it[::GameImp].gameInfo)
                    }
                }
                list.add { game.cancelGameTask(); start(game, true) }
                game.addTask(ITask.repeat(plugin, 20, 20, *(list.toTypedArray())))
            }
        }
    }

    fun stop(game: Game, force: Boolean, leftType: LeftType = LeftType.GAME_STOP) {
        if (game.gameState !== GameState.STOP) {
            game.gameState = GameState.STOP
            finishGame(game)
            ArrayList(game.gameJoined).forEach { gPlayer ->
                leftGame(gPlayer, leftType)
            }
            game.cancelGameTask()
            Bukkit.getPluginManager().callEvent(GameUnloadEvent(game))
            if (game.gameState === GameState.STOP)
                game.addTask({
                    if (gameInstanceService.has(game)) {
                        game.cancelGameTask()
                    }; gameInstanceService.remove(game) }.delay(plugin, game.stopWaitingTick.toLong()))
        }
    }

    private fun finishGame(game: Game) {
        plugin.server.pluginManager.callEvent(GameFinishEvent(game))
    }

    fun beginGame(game: Game) {
        game.gameJoined.forEach { it[::GameAlertImp].GAME_START.send(it, it[::GameImp].gameName)}
    }



    fun afterParticle(game: Game) {
        game.gameJoined.hasTags(PTag.PLAY).forEach {
            it.world.spawnParticle(Particle.END_ROD, it.eyeLocation.clone(), 20)
        }
    }


}