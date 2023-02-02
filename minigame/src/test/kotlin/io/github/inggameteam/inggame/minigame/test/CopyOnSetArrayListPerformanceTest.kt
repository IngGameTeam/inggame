package io.github.inggameteam.inggame.minigame.test

import io.github.inggameteam.inggame.component.Assert
import java.util.*
import java.util.concurrent.CopyOnWriteArraySet
import kotlin.system.measureTimeMillis

fun main() {
    val list = CopyOnWriteArraySet<String>()
    repeat(100) {
        list.add(UUID.randomUUID().toString())
    }
    val key = UUID.randomUUID().toString()
    val a = measureTimeMillis {
        repeat(100000) {
            try {
                throw AssertionError("asdf")
            } catch (_: Throwable) {

            }        }
    }
    val b = measureTimeMillis {
        repeat(100000) {
            try {
                throw Assert("asdf")
            } catch (_: Throwable) {

            }
        }
    }
    println("$a, $b")
}