package io.github.inggameteam.base

import io.github.inggameteam.minigame.GameAlert.*
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.event.GPlayerDeathEvent
import io.github.inggameteam.scheduler.delay
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority

interface Respawn : SpawnPlayer {

    @Deprecated("EventHandler")
    @EventHandler(priority = EventPriority.LOW)
    fun death(event: GPlayerDeathEvent) {
        if (!isJoined(event.player) || gameState === GameState.WAIT) return
        val gPlayer = event.player
        val isDeadBefore = gPlayer.hasTag(PTag.DEAD)
        if (isDeadBefore) return
        gPlayer.apply {
            inventory.clear()
            gameMode = GameMode.SPECTATOR
            addTask({
                spawn(gPlayer)
            }.delay(plugin, comp.intOrNull("respawn")?.toLong()?: 50L))
            comp.send(PLAYER_DEATH_TO_VOID, gPlayer)
        }
    }


}