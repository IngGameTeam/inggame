package io.github.inggameteam.inggame.minigame.base.game

import io.github.inggameteam.inggame.minigame.base.gameserver.GameServer
import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import io.github.inggameteam.inggame.minigame.base.player.PTag
import io.github.inggameteam.inggame.minigame.component.GameInstanceService
import io.github.inggameteam.inggame.minigame.event.*
import io.github.inggameteam.inggame.utils.ITask
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.hasTags
import io.github.inggameteam.inggame.utils.randomUUID

class GameHelper(
    private val gameInstanceService: GameInstanceService,
    private val gameServer: GameServer,
    private val plugin: IngGamePlugin
) {

    fun createGame(parent: String, game: Game = gameInstanceService[randomUUID(), ::GameImp]): Game {
        gameInstanceService.create(game, parent)
        plugin.server.pluginManager.callEvent(GameLoadEvent(game))
        return game
    }

    fun removeGame(game: Game) {
        gameInstanceService.remove(game)
        plugin.server.pluginManager.callEvent(GameUnloadEvent(game))
    }

    fun requestJoin(joinType: JoinType, gameName: String, join: List<GPlayer>) {
        val joinList = join.run(::ArrayList)
        val event = GameRequestJoinEvent(joinList, joinType, gameName)
        plugin.server.pluginManager.callEvent(event)
        if (event.isCancelled) return
        joinList
            .filter { p -> p.joinedGame != gameServer.hub }
            .forEach { p -> joinGame(gameServer.hub, p) }
        val game = createGame(gameName)
        joinList.forEach { p ->
            joinGame(game, p)
        }
    }

    private fun requestJoin(requestedGame: Game, player: GPlayer, joinType: JoinType, sendMessage: Boolean): Boolean {
        if (requestedGame == gameServer.hub) return true
        val gameAlert = player[::GameAlertImp]
        if (requestedGame.gameJoined.contains(player)) {
            if (sendMessage) gameAlert.GAME_ALREADY_JOINED.send(player, requestedGame.gameName)
        } else if (requestedGame.gameState !== GameState.WAIT && joinType === JoinType.PLAY) {
            if (sendMessage) gameAlert.GAME_CANNOT_JOIN_DUE_TO_STARTED.send(player, requestedGame.gameName)
        } else if (requestedGame.playerLimitAmount > 0
            && requestedGame.gameJoined.hasTags(PTag.PLAY).size >= requestedGame.playerLimitAmount
            && joinType === JoinType.PLAY
        ) {
            if (sendMessage) gameAlert.GAME_CANNOT_JOIN_PLAYER_LIMITED.send(player, requestedGame.gameName)
        } else {
            return true
        }
        return false
    }

    fun joinGame(game: Game, player: GPlayer, joinType: JoinType = JoinType.PLAY): Boolean {
        leftGame(player, LeftType.DUE_TO_MOVE_ANOTHER_GAME)
        val gameAlert = player[::GameAlertImp]
        if (requestJoin(game, player, joinType, true)) {
            gameInstanceService.join(game, player)
            game.gameJoined.forEach { p -> gameAlert.GAME_JOIN.send(p, player, p[::GameImp].gameName) }
            if (joinType === JoinType.PLAY) player.addTag(PTag.PLAY)
            else gameAlert.GAME_START_SPECTATING.send(player, game.gameName)
            plugin.server.pluginManager.callEvent(GameJoinEvent(game, player))

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
        val game = gPlayer.joinedGame
        if (!requestLeft(game, gPlayer, leftType)) return false
        plugin.server.pluginManager.callEvent(GameLeftEvent(gPlayer, game, leftType))

        val gameAlert = gPlayer[::GameAlertImp]
        if (leftType === LeftType.LEFT_SERVER) {
            gameAlert.GAME_LEFT_GAME_DUE_TO_SERVER_LEFT.send(gPlayer, game.gameName)
        } else {
            game.gameJoined.forEach { p -> gameAlert.GAME_LEFT.send(p, gPlayer, p[::GameImp].gameName) }
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
            gameInstanceService.join(gameServer.hub, gPlayer)
        }
        if (game.gameState != GameState.STOP && joinedSize <= if (game.gameState === GameState.PLAY) 1 else 0) {
            stop(game, false)
        }
        return true
    }

    fun start(game: Game, force: Boolean) {
        if (force) {
            game.gameState = GameState.PLAY
            plugin.server.pluginManager.callEvent(GameBeginEvent(game))
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
            plugin.server.pluginManager.callEvent(GameFinishEvent(game))
            ArrayList(game.gameJoined).forEach { gPlayer ->
                leftGame(gPlayer, leftType)
            }
            game.cancelGameTask()
            plugin.server.pluginManager.callEvent(GameUnloadEvent(game))
//            if (game.gameState === GameState.STOP)
//                game.addTask({
//                    if (gameInstanceService.has(game)) {
//                        game.cancelGameTask()
//                    }
//                    removeGame(game)
//                }.delay(plugin, game.stopWaitingTick.toLong()))
        }
    }

}