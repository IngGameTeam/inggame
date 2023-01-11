package io.github.inggameteam.inggame.utils

fun Number.clearZero() = if (this.toInt().toDouble() == this) this.toInt() else this