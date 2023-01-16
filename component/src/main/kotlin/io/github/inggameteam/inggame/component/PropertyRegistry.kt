package io.github.inggameteam.inggame.component

import io.github.inggameteam.inggame.mongodb.Model
import io.github.inggameteam.inggame.mongodb.ModelRegistryAll
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties

class PropertyRegistry(modelRegistryAll: ModelRegistryAll) {

    private val propMap = HashMap<String, KClass<*>>()

    fun getPropClass(prop: String): KClass<*> {
        return propMap[prop]?: throw AssertionError("$prop not found")
    }

    init {
        val classes = modelRegistryAll.models
        val types= classes
            .filter { it.java.getAnnotation(Model::class.java) === null }
            .filter { it.java.getAnnotation(NonElement::class.java) === null }
        types.forEach { clazz ->
            clazz.declaredMemberProperties.map { it.name }.map { Pair(it, clazz) }
                .forEach {
                    if (propMap.containsKey(it.first)) {
                        throw AssertionError("${it.first} duplicated between ${propMap.get(it.first)} and ${it.second}")
                    }
                    propMap[it.first] = it.second
                }
        }
    }

}