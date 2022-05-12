package io.github.inggameteam.minigame.angangang.game.base

import io.github.inggameteam.minigame.*
import io.github.inggameteam.minigame.event.GPlayerDeathEvent
import io.github.inggameteam.minigame.event.GPlayerWinEvent
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.hasNoTags
import io.github.inggameteam.player.hasTags
import io.github.inggameteam.player.toPlayerList
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.event.EventHandler

interface Competition : Sectional, Game {
    val stopCheckPlayer: Int get() = 1
    fun sendDeathMessage(player: GPlayer) {
        comp.send(GameAlert.PLAYER_DEATH_TO_VOID, joined, player)
    }
    fun calcWinner() {
        val winners = joined.hasNoTags(PTag.DEAD).hasTags(PTag.PLAY)
        val dieToReady = joined.hasTags(PTag.DEAD, PTag.PLAY)
        if (winners.isEmpty() && dieToReady.size == 1)
            joined.forEach { comp.send(GameAlert.GAME_DRAW_HAS_WINNER, it, winners, displayName(it)) }
        else if (winners.isEmpty()) {
            joined.forEach { comp.send(GameAlert.GAME_DRAW_NO_WINNER, it, displayName(it)) }
        }
        else {
            joined.forEach { comp.send(GameAlert.SINGLE_WINNER, it, winners, displayName(it)) }
        }
//        winners.forEach{ Context.rewardPoint(it.player, rewardPoint)}
        Bukkit.getPluginManager().callEvent(GPlayerWinEvent(this, winners.toPlayerList()))
    }
}

abstract class CompetitionImpl(plugin: GamePlugin) : SectionalImpl(plugin), Competition, SpawnHealth {
    override val recommendedStartPlayersAmount get() = 2
    @Deprecated("EventHandler")
    @EventHandler
    open fun death(event: GPlayerDeathEvent) {
        if (!isJoined(event.player) || gameState === GameState.WAIT) return
        val gPlayer = plugin[event.player]
        val sendDeathMessage = gameState !== GameState.PLAY
        gPlayer.apply {
            removeTag(PTag.PLAY)
            addTag(PTag.DEAD)
            inventory.clear()
            gameMode = GameMode.SPECTATOR
        }
        if (!sendDeathMessage) sendDeathMessage(gPlayer)
        stopCheck()
    }

    open fun stopCheck() {
        if (gameState !== GameState.PLAY) return
        val playPlayers = joined.hasTags(PTag.PLAY)
        if (playPlayers.hasTags(PTag.DEAD).size == 0 || playPlayers.size <= stopCheckPlayer) {
            stop(false)
        }
    }

    override fun finishGame() {
        calcWinner()
    }


}
