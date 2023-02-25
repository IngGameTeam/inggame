package io.github.inggameteam.inggame.minigame.base.sectional

import io.github.inggameteam.inggame.utils.Model
import org.bson.codecs.pojo.annotations.BsonIgnore
import org.bukkit.Bukkit
import org.bukkit.World

@Model
class Sector(
    var x: Int = 0,
    var y: Int = 0,
    worldName: String? = null
) {
    val world: World get() = worldOrNull!!
    @BsonIgnore
    val worldOrNull: World? = worldName?.run(Bukkit::getWorld)
    fun equals(x: Int, y: Int) = this.x == x && this.y == y
    override fun toString() = "${worldOrNull?.name}-$x@$y"
}
