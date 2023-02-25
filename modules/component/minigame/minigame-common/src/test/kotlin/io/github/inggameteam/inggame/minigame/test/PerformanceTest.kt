package io.github.inggameteam.inggame.minigame.test

import io.github.inggameteam.inggame.component.NameSpaceNotFoundException
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArraySet
import kotlin.system.measureTimeMillis

val randomStr get() = UUID.randomUUID().toString()
fun main() {
    val list = CopyOnWriteArraySet<String>().toMutableList()
    repeat(100) {
        list.add(UUID.randomUUID().toString())
    }
    val key = UUID.randomUUID().toString()
    val map = ConcurrentHashMap<Any, Any>()
    repeat(100) {
        map[randomStr] = randomStr
    }
    val times = 1000000
    val a = measureTimeMillis {
        repeat(times) {
            try {
                throw NameSpaceNotFoundException()
            } catch (_: Throwable) {

            }
        }
    }
    val exception = NameSpaceNotFoundException()
    val b = measureTimeMillis {
        repeat(times) {
            try {
                throw exception
            } catch (_: Throwable) {

            }
        }
    }
    println("$a, $b")
}