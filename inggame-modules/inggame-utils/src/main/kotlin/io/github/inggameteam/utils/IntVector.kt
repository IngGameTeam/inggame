package io.github.inggameteam.utils

class IntVector(val x: Int = 0, val z: Int = 0) {
    fun equals(x: Int, z: Int) = this.x == x && this.z == z
    override fun toString() = "$x@$z"
}
