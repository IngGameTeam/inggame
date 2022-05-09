package io.github.inggameteam.utils

import org.bukkit.Location
import org.bukkit.World

data class LocationWithoutWorld(
    val x: Double, val y: Double, val z: Double, val yaw: Float, val pitch: Float
) {
    fun toLocation(world: World) = Location(world, x, y, z,yaw, pitch)
}