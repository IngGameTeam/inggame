package io.github.inggameteam.inggame.minigame.test

import java.util.*
import kotlin.system.measureNanoTime


private val iterable: MutableList<String> = mutableListOf()
fun setup() {
    repeat(10000) {
        val randomString = UUID.randomUUID().toString()
        iterable.add(randomString)
    }
}


fun main() {
//    fun newRandomString() = UUID.randomUUID().toString()
//    val list = (1..10000).map { newRandomString() }.toMutableList()
    setup()
    val list = iterable
    val times = 100000
    val a = measureNanoTime {
        repeat(times) {
            list.forEach { }
        }
    }
    val b = measureNanoTime {
        repeat(times) {
            list.fastForEach { }
        }
    }
    println("a=$a, b=$b")
}

inline fun <T> List<T>.fastForEach(callback: (T) -> Unit) {
    var n = 0
    while (n < size) callback(this[n++])
}

