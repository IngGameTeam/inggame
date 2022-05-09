package io.github.inggameteam.minigame.base.competition

import io.github.inggameteam.minigame.*
import io.github.inggameteam.minigame.base.SectionalImpl
import io.github.inggameteam.minigame.event.GPlayerDeathEvent
import io.github.inggameteam.minigame.event.GameBeginEvent
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.hasTags
import org.bukkit.GameMode
import org.bukkit.event.EventHandler

abstract class Competition(plugin: GamePlugin, point: Sector) : SectionalImpl(plugin, point) {

    open val stopCheckPlayer get() = 1

    @Deprecated("EventHandler")
    @EventHandler
    fun onBeginGameInitHealth(event: GameBeginEvent) {
        if (event.game !== this) return
        joined.hasTags(PTag.PLAY).forEach { it.health = it.maxHealth }
    }

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
/*
        val stopTask = GTask.delay(0) { stopCheck() }
        gameTask =
            if (gameTask == null) stopTask
            else MultiGTask(gameTask!!, stopTask)
*/
    }

    open fun sendDeathMessage(player: GPlayer) {
        comp.send(GameAlert.PLAYER_DEATH_TO_VOID, player)
    }

    open fun stopCheck() {
        if (gameState !== GameState.PLAY) return
        val playPlayers = joined.hasTags(PTag.PLAY)
        if (playPlayers.hasTags(PTag.DEAD).size == 0 || playPlayers.size <= stopCheckPlayer) {
            stop(false)
        }
    }

    companion object {
        const val START = "start"
    }



}