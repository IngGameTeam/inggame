package io.github.inggameteam.minigame

import org.bukkit.World

class Sector(val x: Int = 0, val y: Int = 0, val worldOrNull: World? = null) {
    val world: World get() = worldOrNull!!
    fun equals(x: Int, y: Int) = this.x == x && this.y == y
    override fun toString() = "$x@$y"
}
