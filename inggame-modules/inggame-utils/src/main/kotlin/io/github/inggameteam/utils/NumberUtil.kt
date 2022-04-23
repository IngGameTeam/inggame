package io.github.inggameteam.utils

fun Number.clearZero() = if (this.toInt().toDouble() == this) this.toInt() else this
