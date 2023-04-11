package io.github.inggameteam.inggame.minigame.base.sectional

import io.github.inggameteam.inggame.minigame.base.game.Game
import org.bukkit.Location
import org.bukkit.util.Vector

interface Sectional : Game {

    var sector: Sector
    val isAllocatedGame: Boolean
    val schematicName: String
    val center: Vector
    val minPoint: Vector
    val maxPoint: Vector
    var unloadingSemaphore: Boolean
    val width: Int
    val height: Int
    fun isInSector(location: Location): Boolean

}