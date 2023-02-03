package io.github.inggameteam.inggame.minigame.test

import io.github.inggameteam.inggame.utils.fastForEach
import java.lang.reflect.ParameterizedType
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Supplier


fun main() {
    val list = listOf<Supplier<String>>(
        Supplier { "A" },
        Supplier { "A" },
//        Supplier { AtomicInteger(10) },
    )
    list.fastForEach {
        val genericSuperclass = it.get()::class.java.genericSuperclass
        val value = if(genericSuperclass is Class<*>) genericSuperclass else  (genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<*>
        println(value.simpleName)
    }
}