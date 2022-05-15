package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.event.GPlayerDeathEvent
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.scheduler.delay
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority

interface Respawn : SpawnPlayer, Competition {

    @Suppress("unused")
    @EventHandler(priority = EventPriority.LOW)
    override fun competitionDeath(event: GPlayerDeathEvent) {
        if (!isJoined(event.player) || gameState === GameState.WAIT) return
        val gPlayer = event.player
        val isDeadBefore = gPlayer.hasTag(PTag.DEAD)
        gPlayer.apply {
            inventory.clear()
            gameMode = GameMode.SPECTATOR
            val isDeadFinally = !testRespawn(gPlayer)
            if (isDeadFinally)  {
                gPlayer.apply {
                    removeTag(PTag.PLAY)
                    addTag(PTag.DEAD)
                }
            } else {
                delayRespawn(this)
            }
            event.isCancelled = true
            if (!isDeadBefore) sendDeathMessage(this)
            if (isDeadFinally) stopCheck()
        }
    }

    fun testRespawn(player: GPlayer) = true

    fun delayRespawn(player: GPlayer) {
        player.apply {
            val originGameMode = gameMode
            addTask({
                addTag(PTag.PLAY)
                gameMode = originGameMode
                spawn(this)
            }.delay(plugin, comp.intOrNull("respawn")?.toLong() ?: 50L))
        }
    }

}