package io.github.inggameteam.inggame.component

import io.github.inggameteam.inggame.mongodb.Model
import io.github.inggameteam.inggame.mongodb.ModelRegistryAll
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaField

class PropertyRegistry(modelRegistryAll: ModelRegistryAll) {

    private val propMap = HashMap<String, KClass<*>>()

    fun getPropClass(prop: String): KClass<*> {
        return propMap[prop]?: throw AssertionError("$prop not found")
    }

    init {
        val classes = modelRegistryAll.models
        val types= classes
            .filter { it.java.getAnnotation(Model::class.java) === null }
        types.forEach { clazz ->
            val suffix = "\$delegate"
            clazz.java.declaredFields
                .filter { it.name.endsWith(suffix) }
                .map { Pair(it.name.substring(0, it.name.length - suffix.length), it.type.kotlin) }
                .forEach {
                    if (propMap.containsKey(it.first)) {
                        throw AssertionError("${it.first} duplicated between ${propMap[it.first]} and ${it.second}")
                    }
                    propMap[it.first] = it.second
                }
        }
    }

}