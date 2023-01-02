package io.github.inggameteam.inggame.component.model

import io.github.inggameteam.inggame.mongodb.Model
import org.bukkit.Bukkit
import org.bukkit.Location

@Model
class Location(
    var world: String,
    var x: Double = 0.0,
    var y: Double = 0.0,
    var z: Double = 0.0,
    var yaw: Float = 0f,
    var pitch: Float = 0f,
) {
    override fun toString() = "$world($x, $y, $z, $yaw, $pitch)"

    fun toLocation() = Location(Bukkit.getWorld(world), x, y, z, yaw, pitch)

}
