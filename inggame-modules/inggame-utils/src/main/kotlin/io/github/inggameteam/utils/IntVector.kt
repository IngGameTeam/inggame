package io.github.inggameteam.utils

class IntVector(val x: Int = 0, val y: Int = 0) {
    fun equals(x: Int, y: Int) = this.x == x && this.y == y
    override fun toString() = "$x@$y"
}
