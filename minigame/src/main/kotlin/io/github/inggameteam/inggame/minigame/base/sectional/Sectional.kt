package io.github.inggameteam.inggame.minigame.base.sectional

import io.github.inggameteam.inggame.minigame.base.game.Game
import io.github.inggameteam.inggame.minigame.base.locational.Locational
import org.bukkit.Location
import org.bukkit.util.Vector

interface Sectional : Game, Locational {

    var gameSector: Sector
    val isAllocatedGame: Boolean
    val schematicName: String
    val center: Vector
    val minPoint: Vector
    val maxPoint: Vector
    var isUnloaded: Boolean
    val gameWidth: Int
    val gameHeight: Int
    fun isInSector(location: Location): Boolean

}