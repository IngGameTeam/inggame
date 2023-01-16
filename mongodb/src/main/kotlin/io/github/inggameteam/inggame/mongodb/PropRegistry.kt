package io.github.inggameteam.inggame.mongodb

import kotlin.reflect.KClass

class PropRegistry(
    vararg clazz: KClass<*>
) {
    val models = HashSet<KClass<*>>()
    val wrappers = HashSet<KClass<*>>()
    init { register(*clazz) }
    val all get() = listOf(*models.toTypedArray(), *wrappers.toTypedArray())
    fun register(vararg clazz: KClass<*>) {
        clazz.forEach {
            if (it.java.getAnnotation(Model::class.java) !== null)
                models.add(it)
            else wrappers.add(it)
        }
    }
}