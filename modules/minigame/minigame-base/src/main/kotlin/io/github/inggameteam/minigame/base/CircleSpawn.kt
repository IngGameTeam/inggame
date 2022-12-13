package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.hasTags
import org.bukkit.Location
import kotlin.math.cos
import kotlin.math.sin

interface CircleSpawn : Sectional, SpawnPlayer {

    override fun tpSpawn(player: GPlayer, spawn: String): Location? {
        if (gameState !== GameState.PLAY) return null
        val center = super.tpSpawn(player, spawn) ?: throw AssertionError("no spawn location")
        val players = joined.hasTags(PTag.PLAY)
        val i = players.indexOf(player)
        var x: Double
        var z: Double
        var angle = 2 * Math.PI * i / players.size
        val radius = comp.doubleOrNull("circle-spawn-radius") ?: 2.5
        x = cos(angle) * radius
        z = sin(angle) * radius
        return center.clone().add(x, 0.0, z)
    }

}