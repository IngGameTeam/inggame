package io.github.inggameteam.inggame.minigame.handler

import io.github.inggameteam.inggame.minigame.wrapper.game.Sectional
import org.bukkit.Location
import org.bukkit.entity.EntityType

class SectionalHelper {

    fun clearEntitiesToUnload(sectional: Sectional): Unit = sectional.run {
        val before = System.currentTimeMillis()
        gameSector.world.getNearbyEntities(
            Location(gameSector.world,
                gameSector.x * gameWidth.toDouble(),
                gameHeight.toDouble(),
                gameSector.y * gameWidth.toDouble()
            ), 150.0, 150.0, 150.0).apply {
            println("clearEntities(nearBy):${System.currentTimeMillis() - before}ms")
        }.forEach {
            if (it.type != EntityType.PLAYER) it.remove()
        }
        println("clearEntities(remove):${System.currentTimeMillis() - before}ms")
    }


}