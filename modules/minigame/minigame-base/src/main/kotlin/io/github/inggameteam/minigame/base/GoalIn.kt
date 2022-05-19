package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.player.hasTags
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerMoveEvent

interface GoalIn : Game, Sectional, Competition {

    @Suppress("unused")
    @EventHandler
    fun moveGoalIn(event: PlayerMoveEvent) {
        val player = plugin[event.player]
        if (isJoined(player)) {
            if (
                player.location.toVector().isInAABB(
                    getLocation("goal_pos1").toVector(),
                    getLocation("goal_pos2").toVector()
                )
            )
            {
                joined
                    .hasTags(PTag.PLAY)
                    .filter { it != player }
                    .forEach { it.addTag(PTag.DEAD); it.removeTag(PTag.PLAY) }
                stopCheck()
            }
        }
    }

}