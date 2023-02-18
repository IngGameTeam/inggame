package io.github.inggameteam.inggame.utils

import kotlin.reflect.KClass

open class ClassRegistry(
    vararg clazz: KClass<*>
) {
    val classes = HashSet<KClass<*>>()
    init { register(*clazz) }
    fun register(vararg clazz: KClass<*>) {
        clazz.forEach { classes.add(it) }
    }
}