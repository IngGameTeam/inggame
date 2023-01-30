package io.github.inggameteam.inggame.minigame.wrapper.game

import org.bukkit.Location
import org.bukkit.util.Vector
import java.io.File

interface Sectional : Game {

    val schematicName: String
    val minPoint: Vector
    val maxPoint: Vector
    var isUnloaded: Boolean
    val gameWidth: Int
    val gameHeight: Int
    fun loadSector(key: String = schematicName)
    fun loadDefaultSector() = loadSector(schematicName)
    fun unloadSector()
    fun isInSector(location: Location): Boolean
    fun getLocation(key: String): Location
    fun getLocationOrNull(key: String): Location?
    fun getSchematicFile(name: String, dir: String): File


}