package io.github.inggameteam.inggame.minigame.test

import kotlin.concurrent.thread

fun main() {
    val singletonThrow = Throwable("asdfasdf")
    val times = 100000
    repeat (100) {
        thread { repeat (times) { try { throw singletonThrow } catch (_: Throwable) { } } }
    }
}