package io.github.inggameteam.inggame.minigame.base.game

import io.github.inggameteam.inggame.minigame.base.game.event.*
import io.github.inggameteam.inggame.minigame.base.gameserver.GameServer
import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import io.github.inggameteam.inggame.minigame.base.player.PTag
import io.github.inggameteam.inggame.minigame.component.GameInstanceService
import io.github.inggameteam.inggame.player.container.ContainerAlertImp
import io.github.inggameteam.inggame.player.container.ContainerHelperBase
import io.github.inggameteam.inggame.utils.*
import org.bukkit.Bukkit

class GameHelper(
    private val gameInstanceService: GameInstanceService,
    val gameServer: GameServer,
    val plugin: IngGamePlugin
) : ContainerHelperBase<Game, GPlayer>(gameInstanceService, gameInstanceService, { gameServer.hub }) {

    fun createContainer(parent: String): Game {
        return createContainer(parent, gameInstanceService[randomUUID(), ::GameImp])
    }

    override fun createContainer(parent: String, container: Game): Game {
        return super.createContainer(parent, container).also {
            Bukkit.getPluginManager().callEvent(GameLoadEvent(container))
        }
    }

    override fun removeContainer(container: Game) {
        Bukkit.getPluginManager().callEvent(GameUnloadEvent(container))
        super.removeContainer(container)
    }

    fun requestJoin(joinType: JoinType, gameName: String, join: List<GPlayer>) {
        val joinList = join.run(::ArrayList)
        val event = GameRequestJoinEvent(joinList, joinType, gameName)
        plugin.server.pluginManager.callEvent(event)
        if (event.isCancelled) return
        joinList
            .filter { p -> p.joinedGame != gameServer.hub }
            .forEach { p -> joinContainer(gameServer.hub, p) }
        val game = createContainer(gameName)
        joinList.forEach { p ->
            joinContainer(game, p)
        }
    }

    override fun left(element: GPlayer, container: Game, leftType: LeftType) {
        val joinedSize = container.containerJoined.filter { it.isPlaying }.size
        if (container.containerState === ContainerState.WAIT
            && joinedSize < container.startPlayersAmount && container.hasGameTask()) {
            container.containerJoined.forEach { it[::GameAlertImp].GAME_START_CANCELLED_DUE_TO_PLAYERLESS.send(it) }
            container.cancelGameTask()
        }

    }

    private fun requestJoin(requestedGame: Game, player: GPlayer, joinType: JoinType, sendMessage: Boolean): Boolean {
        if (requestedGame == gameServer.hub) return true
        val alert = player[::ContainerAlertImp]
        if (requestedGame.containerJoined.contains(player)) {
            if (sendMessage) alert.GAME_ALREADY_JOINED.send(player, requestedGame.containerName)
        } else if (requestedGame.containerState !== ContainerState.WAIT && joinType === JoinType.PLAY) {
            if (sendMessage) alert.GAME_CANNOT_JOIN_DUE_TO_STARTED.send(player, requestedGame.containerName)
        } else if (requestedGame.playerLimitAmount > 0
            && requestedGame.containerJoined.hasTags(PTag.PLAY).size >= requestedGame.playerLimitAmount
            && joinType === JoinType.PLAY
        ) {
            if (sendMessage) alert.GAME_CANNOT_JOIN_PLAYER_LIMITED.send(player, requestedGame.containerName)
        } else {
            return true
        }
        return false
    }

    fun start(game: Game, force: Boolean) {
        if (force) {
            game.containerState = ContainerState.PLAY
            Bukkit.getPluginManager().callEvent(GameBeginEvent(game))
        } else if (game.containerState === ContainerState.WAIT) {
            val tick = game.startWaitingSecond
            if (tick < 0) {
                start(game, true)
            } else {
                val list = ArrayList<() -> Unit>()
                for (i in tick downTo  1) list.add {
                    game.containerJoined.forEach {
                        it[::GameAlertImp].GAME_START_COUNT_DOWN.send(it, it[::GameImp].containerName, i, it[::GameImp].gameInfo)
                    }
                }
                list.add { game.cancelGameTask(); start(game, true) }
                game.addTask(ITask.repeat(plugin, 20, 20, *(list.toTypedArray())))
            }
        }
    }

    override fun stop(container: Game, force: Boolean, leftType: LeftType) {
        if (container.containerState !== ContainerState.STOP) {
            container.containerState = ContainerState.STOP
            Bukkit.getPluginManager().callEvent(GameFinishEvent(container))
            ArrayList(container.containerJoined).forEach { gPlayer ->
                leftGame(gPlayer, leftType)
            }
            container.cancelGameTask()
            Bukkit.getPluginManager().callEvent(GameUnloadEvent(container))
        }
    }

}