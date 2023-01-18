package io.github.inggameteam.inggame.utils

import kotlin.reflect.KClass

class ClassRegistry(
    vararg clazz: KClass<*>
) {
    val models = HashSet<KClass<*>>()
    init { register(*clazz) }
    fun register(vararg clazz: KClass<*>) {
        clazz.forEach { models.add(it) }
    }
}