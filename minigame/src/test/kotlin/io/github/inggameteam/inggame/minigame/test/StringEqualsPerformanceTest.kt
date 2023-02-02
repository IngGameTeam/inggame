package io.github.inggameteam.inggame.minigame.test

import java.util.*
import kotlin.system.measureTimeMillis

fun main() {
    val str1 = String(UUID.randomUUID().toString().toByteArray())
    val str2 = String(UUID.randomUUID().toString().toByteArray())
    measureTimeMillis {
        repeat(100000) { str1.equals(str2) }
    }.apply { println(this) }
}