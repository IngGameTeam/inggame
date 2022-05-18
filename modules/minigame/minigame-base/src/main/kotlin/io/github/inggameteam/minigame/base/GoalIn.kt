package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.player.hasTags
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerMoveEvent

interface GoalIn : Game, Sectional {

    @Suppress("unused")
    @EventHandler
    fun moveGoalIn(event: PlayerMoveEvent) {
        val player = plugin[event.player]
        if (isJoined(player)) {
            if (player.location.apply {
                    if (block.type == Material.AIR) add(0.0, -1.0, 0.0)
                }.toVector().isInAABB(getLocation("goal_pos1").toVector(), getLocation("goal_pos2").toVector())) {
                joined.hasTags(PTag.PLAY).filter { player != it }
                    .forEach { it.damage(10000.0) }
            }
        }
    }

}