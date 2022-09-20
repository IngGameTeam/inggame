package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.event.GPlayerDeathEvent
import io.github.inggameteam.minigame.event.GPlayerSpawnEvent
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.scheduler.delay
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority

interface Respawn : SpawnPlayer, Competition {

    val recommendedSpawnDelay get() = 50L

    @Suppress("unused")
    @EventHandler(priority = EventPriority.LOW)
    override fun competitionDeath(event: GPlayerDeathEvent) {
        if (!isJoined(event.player) || gameState === GameState.WAIT) return
        val gPlayer = event.player
        val isDeadBefore = gPlayer.hasTag(PTag.DEAD)
        if (gPlayer.hasTag(PTag.RESPAWN)) {
            event.isCancelled = true
            return
        }
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
            if (isDeadFinally) requestStop()
        }
    }

    fun testRespawn(player: GPlayer) = true

    fun delayRespawn(player: GPlayer) {
        val delay = comp.intOrNull("respawn")?.toLong() ?: recommendedSpawnDelay
        player.apply {
            addTag(PTag.RESPAWN)
            val originGameMode = gameMode
            val function = {
                removeTag(PTag.RESPAWN)
                addTag(PTag.PLAY)
                gameMode = originGameMode
                Bukkit.getPluginManager().callEvent(GPlayerSpawnEvent(player))
            }
            if (delay >= 0) addTask(function.delay(plugin, delay))
            else function()
        }
    }

}