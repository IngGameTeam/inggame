package io.github.inggameteam.inggame.minigame.test

import java.util.*
import kotlin.system.measureTimeMillis

fun main() {
    val list = (ArrayList<String>())
    repeat(100) {
        list.add(UUID.randomUUID().toString())
    }
    val key = UUID.randomUUID().toString()
    measureTimeMillis {
        repeat(100000) {
            list.forEach {  }
        }
    }.apply { println(this) }

}