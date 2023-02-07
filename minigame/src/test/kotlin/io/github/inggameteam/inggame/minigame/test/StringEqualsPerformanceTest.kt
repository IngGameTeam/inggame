package io.github.inggameteam.inggame.minigame.test

import io.github.inggameteam.inggame.utils.fastForEach
import java.util.*
import kotlin.system.measureTimeMillis

fun randomUUIDString() = String(UUID.randomUUID().toString().toByteArray())

fun main() {
    val list = arrayListOf<String>()
    repeat(100000) { list.add(randomUUIDString()) }
    val key = randomUUIDString()
    measureTimeMillis {

    }.apply { println(this) }
}