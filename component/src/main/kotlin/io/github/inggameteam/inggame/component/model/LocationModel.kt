package io.github.inggameteam.inggame.component.model

import io.github.inggameteam.inggame.mongodb.Model
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World

@Model
class LocationModel(
    var world: String,
    var x: Double = 0.0,
    var y: Double = 0.0,
    var z: Double = 0.0,
    var yaw: Float = 0f,
    var pitch: Float = 0f,
    var isRelative: Boolean
) {
    override fun toString() = "$world($x, $y, $z, $yaw, $pitch)"

    fun toLocation(world: World? = null) =
        Location(if (isRelative && world !== null) world else Bukkit.getWorld(this.world), x, y, z, yaw, pitch)

}
