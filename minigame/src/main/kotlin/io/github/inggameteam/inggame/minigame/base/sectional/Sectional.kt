package io.github.inggameteam.inggame.minigame.base.sectional

import io.github.inggameteam.inggame.component.model.LocationModel
import io.github.inggameteam.inggame.minigame.base.game.Game
import org.bukkit.Location
import org.bukkit.util.Vector

interface Sectional : Game {

    var gameSector: Sector
    val isAllocatedGame: Boolean
    val schematicName: String
    val schematicLocations: HashMap<String, HashMap<String, LocationModel>>
    val minPoint: Vector
    val maxPoint: Vector
    var isUnloaded: Boolean
    val gameWidth: Int
    val gameHeight: Int
    fun isInSector(location: Location): Boolean
    fun getLocation(key: String): Location
    fun getLocationOrNull(key: String): Location?

}