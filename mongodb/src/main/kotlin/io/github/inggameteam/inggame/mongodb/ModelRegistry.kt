package io.github.inggameteam.inggame.mongodb

import kotlin.reflect.KClass

class ModelRegistry(
    vararg clazz: KClass<*>
) {
    val models = HashSet<KClass<*>>()
    init { register(*clazz) }
    fun register(vararg clazz: KClass<*>) {
        clazz.forEach { models.add(it) }
    }
}