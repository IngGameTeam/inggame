//package io.github.inggameteam.inggame.minigame.base.game
//
//import io.github.inggameteam.inggame.minigame.base.game.event.*
//import io.github.inggameteam.inggame.minigame.base.gameserver.GameServer
//import io.github.inggameteam.inggame.minigame.base.player.GPlayer
//import io.github.inggameteam.inggame.minigame.base.player.PTag
//import io.github.inggameteam.inggame.minigame.component.GameInstanceService
//import io.github.inggameteam.inggame.utils.*
//
//class OldGameHelper(
//    private val gameInstanceService: GameInstanceService,
//    private val gameServer: GameServer,
//    private val plugin: IngGamePlugin
//) {
//
//    fun createGame(parent: String, game: Game = gameInstanceService[randomUUID(), ::GameImp]): Game {
//        return gameInstanceService.create(game, parent).also {
//            plugin.server.pluginManager.callEvent(GameLoadEvent(game))
//        }
//    }
//
//    fun removeGame(game: Game) {
//        plugin.server.pluginManager.callEvent(GameUnloadEvent(game))
//        gameInstanceService.remove(game)
//    }
//
//    fun requestJoin(joinType: JoinType, gameName: String, join: List<GPlayer>) {
//        val joinList = join.run(::ArrayList)
//        val event = GameRequestJoinEvent(joinList, joinType, gameName)
//        plugin.server.pluginManager.callEvent(event)
//        if (event.isCancelled) return
//        joinList
//            .filter { p -> p.joinedGame != gameServer.hub }
//            .forEach { p -> joinGame(gameServer.hub, p) }
//        val game = createGame(gameName)
//        joinList.forEach { p ->
//            joinGame(game, p)
//        }
//    }
//
//    private fun requestJoin(requestedGame: Game, player: GPlayer, joinType: JoinType, sendMessage: Boolean): Boolean {
//        if (requestedGame == gameServer.hub) return true
//        val gameAlert = player[::GameAlertImp]
//        if (requestedGame.containerJoined.contains(player)) {
//            if (sendMessage) gameAlert.GAME_ALREADY_JOINED.send(player, requestedGame.containerName)
//        } else if (requestedGame.containerState !== ContainerState.WAIT && joinType === JoinType.PLAY) {
//            if (sendMessage) gameAlert.GAME_CANNOT_JOIN_DUE_TO_STARTED.send(player, requestedGame.containerName)
//        } else if (requestedGame.playerLimitAmount > 0
//            && requestedGame.containerJoined.hasTags(PTag.PLAY).size >= requestedGame.playerLimitAmount
//            && joinType === JoinType.PLAY
//        ) {
//            if (sendMessage) gameAlert.GAME_CANNOT_JOIN_PLAYER_LIMITED.send(player, requestedGame.containerName)
//        } else {
//            return true
//        }
//        return false
//    }
//
//    fun joinGame(game: Game, player: GPlayer, joinType: JoinType = JoinType.PLAY): Boolean {
//        leftGame(player, LeftType.DUE_TO_MOVE_ANOTHER)
//        val gameAlert = player[::GameAlertImp]
//        if (requestJoin(game, player, joinType, true)) {
//            gameInstanceService.join(game, player)
//            game.containerJoined.forEach { p -> gameAlert.GAME_JOIN.send(p, player, p[::GameImp].containerName) }
//            if (joinType === JoinType.PLAY) player.addTag(PTag.PLAY)
//            else gameAlert.GAME_START_SPECTATING.send(player, game.containerName)
//            plugin.server.pluginManager.callEvent(GameJoinEvent(game, player))
//
//            if (!game.hasGameTask()
//                && game.containerState === ContainerState.WAIT
//                && 0 < game.startPlayersAmount
//                && game.containerJoined.hasTags(PTag.PLAY).size >= game.startPlayersAmount)
//                start(game, false)
//            return true
//        }
//        return false
//    }
//
//    private fun requestLeft(game: Game, gPlayer: GPlayer, leftType: LeftType) = game.containerJoined.contains(gPlayer)
//
//    fun leftGame(gPlayer: GPlayer, leftType: LeftType): Boolean {
//        val game = gPlayer.joinedGame
//        if (!requestLeft(game, gPlayer, leftType)) return false
//        plugin.server.pluginManager.callEvent(GameLeftEvent(gPlayer, game, leftType))
//
//        val gameAlert = gPlayer[::GameAlertImp]
//        if (leftType === LeftType.LEFT_SERVER) {
//            gameAlert.GAME_LEFT_GAME_DUE_TO_SERVER_LEFT.send(gPlayer, game.containerName)
//        } else {
//            game.containerJoined.forEach { p -> gameAlert.GAME_LEFT.send(p, gPlayer, p[::GameImp].containerName) }
//        }
//        gPlayer.clearTags()
//        gameInstanceService.left(gPlayer)
//        val joinedSize = game.containerJoined.hasTags(PTag.PLAY).size
//        if (game.containerState === ContainerState.WAIT
//            && joinedSize < game.startPlayersAmount && game.hasGameTask()) {
//            game.containerJoined.forEach { it[::GameAlertImp].GAME_START_CANCELLED_DUE_TO_PLAYERLESS.send(it) }
//            game.cancelGameTask()
//        }
//        if (leftType.isJoinHub) {
//            joinGame(gameServer.hub, gPlayer)
//        }
//        if (game.containerState != ContainerState.STOP && joinedSize <= if (game.containerState === ContainerState.PLAY) 1 else 0) {
//            stop(game, false)
//        }
//        return true
//    }
//
//    fun start(game: Game, force: Boolean) {
//        if (force) {
//            game.containerState = ContainerState.PLAY
//            plugin.server.pluginManager.callEvent(GameBeginEvent(game))
//        } else if (game.containerState === ContainerState.WAIT) {
//            val tick = game.startWaitingSecond
//            if (tick < 0) {
//                start(game, true)
//            } else {
//                val list = ArrayList<() -> Unit>()
//                for (i in tick downTo  1) list.add {
//                    game.containerJoined.forEach {
//                        it[::GameAlertImp].GAME_START_COUNT_DOWN.send(it, it[::GameImp].containerName, i, it[::GameImp].gameInfo)
//                    }
//                }
//                list.add { game.cancelGameTask(); start(game, true) }
//                game.addTask(ITask.repeat(plugin, 20, 20, *(list.toTypedArray())))
//            }
//        }
//    }
//
//    fun stop(game: Game, force: Boolean, leftType: LeftType = LeftType.STOP) {
//        if (game.containerState !== ContainerState.STOP) {
//            game.containerState = ContainerState.STOP
//            plugin.server.pluginManager.callEvent(GameFinishEvent(game))
//            ArrayList(game.containerJoined).forEach { gPlayer ->
//                leftGame(gPlayer, leftType)
//            }
//            game.cancelGameTask()
//            plugin.server.pluginManager.callEvent(GameUnloadEvent(game))
////            if (game.gameState === GameState.STOP)
////                game.addTask({
////                    if (gameInstanceService.has(game)) {
////                        game.cancelGameTask()
////                    }
////                    removeGame(game)
////                }.delay(plugin, game.stopWaitingTick.toLong()))
//        }
//    }
//
//}