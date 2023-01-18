package io.github.inggameteam.inggame.component

import io.github.inggameteam.inggame.mongodb.Model
import io.github.inggameteam.inggame.mongodb.ModelRegistryAll
import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.jvm.kotlinProperty

class PropertyRegistry(modelRegistryAll: ModelRegistryAll) {

    private val propMap = HashMap<String, KType>()

    fun getProp(prop: String): KType {
        return propMap[prop]?: throw AssertionError("$prop not found")
    }

    init {
        val classes = modelRegistryAll.models
        classes
            .filter { it.java.getAnnotation(PropHandler::class.java) !== null }
            .forEach {
                propMap[it.simpleName!!] = Boolean::class.createType()
            }
        val types = classes
            .filter { it.java.getAnnotation(Model::class.java) === null }
            .filter { it.java.getAnnotation(Wrapper::class.java) !== null }
        types.forEach { clazz ->
            val suffix = "\$delegate"
            clazz.java.declaredFields
                .filter { it.name.endsWith(suffix) }
                .map { Pair(it.name.substring(0, it.name.length - suffix.length), it.kotlinProperty?.returnType!!) }
                .forEach {
                    if (propMap.containsKey(it.first)) {
                        throw AssertionError("${it.first} duplicated between ${propMap[it.first]} and ${it.second}")
                    }
                    propMap[it.first] = it.second
                }
        }
    }

}