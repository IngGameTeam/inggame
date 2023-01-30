package io.github.inggameteam.inggame.minigame.wrapper.game

import org.bukkit.Location
import org.bukkit.util.Vector

interface Sectional : Game {

    val schematicName: String
    val minPoint: Vector
    val maxPoint: Vector
    var isUnloaded: Boolean
    val gameWidth: Int
    val gameHeight: Int
    fun isInSector(location: Location): Boolean
    fun getLocation(key: String): Location
    fun getLocationOrNull(key: String): Location?

}