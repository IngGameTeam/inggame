package io.github.inggameteam.minigame

class Sector(val x: Int = 0, val z: Int = 0) {
    fun equals(x: Int, z: Int) = this.x == x && this.z == z
    override fun toString() = "$x@$z"
}
