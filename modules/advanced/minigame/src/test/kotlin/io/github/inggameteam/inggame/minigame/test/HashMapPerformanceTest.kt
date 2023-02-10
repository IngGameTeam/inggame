package io.github.inggameteam.inggame.minigame.test

import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.system.measureTimeMillis

fun main() {
    val map = ConcurrentHashMap<String, String>()
    repeat(100) {
        map[UUID.randomUUID().toString()] = UUID.randomUUID().toString()
    }
    val key = UUID.randomUUID().toString()
    measureTimeMillis {
        repeat(100000) {
            try {
                throw AssertionError("asdf")
            } catch (_: Throwable) {

            }
        }
    }.apply { println(this) }

}