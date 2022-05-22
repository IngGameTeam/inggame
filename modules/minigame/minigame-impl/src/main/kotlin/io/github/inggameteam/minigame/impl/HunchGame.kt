package io.github.inggameteam.minigame.impl

import io.github.inggameteam.minigame.GameAlert
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.base.CompetitionImpl
import io.github.inggameteam.minigame.base.SimpleGame
import io.github.inggameteam.player.hasTags
import io.github.inggameteam.scheduler.ITask
import io.github.inggameteam.scheduler.runNow
import org.bukkit.event.EventHandler
import org.bukkit.event.player.AsyncPlayerChatEvent

class HunchGame(plugin: GamePlugin) : SimpleGame, CompetitionImpl(plugin) {
    override val name get() = "hunch-game"
    var countedNumber = 0

    @EventHandler
    fun chat(event: AsyncPlayerChatEvent) {
        if (gameState !== GameState.PLAY) return
        val player = event.player
        val gPlayer = plugin[player]
        val gPlayerData = playerData[gPlayer]
        if (isJoined(player)) {
            event.isCancelled = true
        } else return
        if (!gPlayer.hasTag(PTag.PLAY) || (gPlayerData != null && gPlayerData[CHATTED] != null)) return
        val message = event.message
        val typedNumber = parseInt(message)
        if (typedNumber == -1) {
            comp.send("cannot_chat", gPlayer)
            return
        }
        {
            comp.send("chat_format", joined, gPlayer, message)
            val checker = {
                if (!player.isOnline) {
                } else if (typedNumber != countedNumber + 1) {
                    gPlayerData!!.remove(CHATTED)
                    player.damage(10000.0)
                    playNextTurn()
                } else {
                    countedNumber++
                    gPlayerData!![CHATTED] = true
                    stopCheck()
                }
            }.runNow(plugin)
            addTask(checker)
        }.runNow(plugin)
    }



    override fun stopCheck() {
        if (gameState !== GameState.PLAY) return
        val playPlayers = joined.hasTags(PTag.PLAY)
        if (countedNumber >= playPlayers.size - 1) {
            val chattedPlayers = playPlayers.filter { playerData[it]!![CHATTED] != null }
            if (chattedPlayers.size <= 1) {
                if (chattedPlayers.isEmpty()) return
                val chattedWinner = chattedPlayers.first()
                playPlayers.filter { it != chattedWinner }.forEach { it.removeTag(PTag.PLAY); it.addTag(PTag.DEAD)}
                stop(false)
            } else {
                playPlayers.filter { !chattedPlayers.contains(it) }.forEach { it.damage(10000.0) }
                playNextTurn()
            }
        }
    }

    private fun playNextTurn() {
        val alertName = "countdown"
        joined.hasTags(PTag.PLAY).forEach { playerData[it]!![CHATTED] = true }
        gameTask = ITask.repeat(plugin, 0, 20,
            {comp.send(alertName, joined, 3)},
            {comp.send(alertName, joined, 2)},
            {comp.send(alertName, joined, 1)},
            {
                comp.send(GameAlert.GAME_START, joined, "")
                countedNumber = 0
                joined.hasTags(PTag.PLAY).forEach { playerData[it]!!.remove(CHATTED) }
            }
        )
    }

    private fun parseInt(message: String) =
        try {
            Integer.parseInt(message)
        } catch (_: Exception) {
            -1
        }

    companion object {
        const val CHATTED = "chatted"
    }

}