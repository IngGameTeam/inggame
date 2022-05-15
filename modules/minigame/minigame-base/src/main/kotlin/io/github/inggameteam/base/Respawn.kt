package io.github.inggameteam.base

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
    @EventHandler(priority = EventPriority.HIGH)
    fun respawnOnDeath(event: GPlayerDeathEvent) {
        if (!isJoined(event.player) || gameState === GameState.WAIT) return
        val gPlayer = event.player
        val isDeadBefore = gPlayer.hasTag(PTag.DEAD)
        if (!testRespawn(gPlayer)) return
        gPlayer.apply {
            delayRespawn(this)
            if (!isDeadBefore) sendDeathMessage(this)
        }
    }

    fun testRespawn(player: GPlayer) = true

    fun delayRespawn(player: GPlayer) {
        player.apply {
            addTask({
                addTag(PTag.PLAY)
                spawn(this)
            }.delay(plugin, comp.intOrNull("respawn")?.toLong() ?: 50L))
        }
    }

}