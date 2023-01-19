package io.github.inggameteam.inggame.component

import io.github.inggameteam.inggame.mongodb.ClassRegistryAll
import io.github.inggameteam.inggame.utils.ClassRegistry
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

class SubClassRegistry(classRegistry: ClassRegistryAll) {

    val map = HashMap<KClass<*>, ArrayList<KClass<*>>>()

    fun getSubs(kClass: KClass<*>): Collection<KClass<*>> = map[kClass]
        ?: throw AssertionError("no sub class found on ${kClass.simpleName}")

    init {
        val classes = classRegistry.classes
        classes.forEach { clazz ->
            if (clazz.java.isInterface) {
                map[clazz] = classes.filter {
                    println(it)
                    it.isSubclassOf(clazz) }.run(::ArrayList)
            }
        }
    }

}