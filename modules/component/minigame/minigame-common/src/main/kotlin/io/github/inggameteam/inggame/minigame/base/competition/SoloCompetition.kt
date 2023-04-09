package io.github.inggameteam.inggame.minigame.base.competition

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.minigame.base.competition.event.GPlayerWinEvent
import io.github.inggameteam.inggame.minigame.base.game.*
import io.github.inggameteam.inggame.minigame.base.game.event.GPlayerDeathEvent
import io.github.inggameteam.inggame.minigame.base.game.event.GameFinishEvent
import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import io.github.inggameteam.inggame.minigame.base.player.PTag
import io.github.inggameteam.inggame.minigame.component.GameInstanceService
import io.github.inggameteam.inggame.utils.ContainerState
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.hasNoTags
import io.github.inggameteam.inggame.utils.hasTags
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.event.EventHandler

class SoloCompetitionHandler(
    private val gameInstanceService: GameInstanceService,
    private val gameHelper: GameHelper,
    plugin: IngGamePlugin
) : HandleListener(plugin), CompetitionHandler {

    override fun sendDeathMessage(player: GPlayer, killer: GPlayer?) {
        player[::GameImp].joinedPlayers.forEach { p -> p[::GameAlertImp].PLAYER_DEATH_TO_VOID.send(p, player) }
    }

    @Suppress("unused")
    @EventHandler
    override fun handleDeath(event: GPlayerDeathEvent) {
        val player = event.player
        if (isNotHandler(player)) return
        val game = player.joined
        if (game.containerState === ContainerState.WAIT) return
        val sendDeathMessage = game.containerState !== ContainerState.PLAY
        player.apply {
            removeTag(PTag.PLAY)
            addTag(PTag.DEAD)
            inventory.clear()
            gameMode = GameMode.SPECTATOR
        }
        event.isCancelled = true
        if (!sendDeathMessage) {
            val killer = event.killer?.run { gameInstanceService[uniqueId, ::GPlayer] }
            sendDeathMessage(player, killer)
        }
        requestStop(game)
    }

    @Suppress("unused")
    @EventHandler
    fun onFinishGame(event: GameFinishEvent) {
        val game = event.game
        if (isNotHandler(game)) return
        calcWinner(game)
    }


    override fun requestStop(game: Game) = game.run {
        if (containerState !== ContainerState.PLAY) return
        val playPlayers = joinedPlayers.hasTags(PTag.PLAY)
        if (
            playPlayers.hasNoTags(PTag.DEAD).size == 0
            || playPlayers.size <= this[::CompetitionImp].stopCheckPlayer
        ) {
            gameHelper.stop(game, false)
        }
    }

    override fun calcWinner(game: Game) = game.run {
        val winners = joinedPlayers.hasNoTags(PTag.DEAD).hasTags(PTag.PLAY)
        val dieToReady = joinedPlayers.hasTags(PTag.DEAD, PTag.PLAY)
        if (winners.isEmpty() && dieToReady.size == 1)
            joinedPlayers.forEach { p -> p[::GameAlertImp].GAME_DRAW_HAS_WINNER.send(p, dieToReady,
                containerName
            ) }
        else if (winners.isEmpty()) {
            joinedPlayers.forEach { p -> p[::GameAlertImp].GAME_DRAW_NO_WINNER.send(p, dieToReady,
                containerName
            ) }
        } else {
            joinedPlayers.forEach { p -> p[::GameAlertImp].SINGLE_WINNER.send(p, dieToReady, containerName) }
        }
//        winners.forEach{ Context.rewardPoint(it.player, rewardPoint)}
        Bukkit.getPluginManager().callEvent(GPlayerWinEvent(this, winners))
    }

}