package io.github.inggameteam.inggame.component

import io.github.inggameteam.inggame.component.delegate.Wrapper
import io.github.inggameteam.inggame.mongodb.ClassRegistryAll
import io.github.inggameteam.inggame.mongodb.Model
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.javaField

class PropertyRegistry(classRegistryAll: ClassRegistryAll) {

    data class Prop(val name: String, val type: KType, val clazz: KClass<*>)

    private val propMap = ArrayList<Prop>()

    fun getAllProp(): MutableList<Prop> {
        return propMap.toMutableList()
    }

    fun getProp(prop: String): Prop {
        return propMap.firstOrNull { it.name == prop }?: throw AssertionError("$prop not found")
    }

    init {
        val classes = classRegistryAll.classes
        classes
            .filter { it.isSubclassOf(Handler::class) }
            .forEach {
                propMap.add(Prop(it.simpleName!!, Boolean::class.createType(), Boolean::class))
            }
        val types = classes
            .filter { it.java.getAnnotation(Model::class.java) === null }
            .filter { it.isSubclassOf(Wrapper::class) }
        types.forEach { clazz ->
            val suffix = "\$delegate"
            clazz.declaredMemberProperties
                .filter {
                    it.javaField?.type == clazz.java
                }
                .map { Pair(
                    if (it.name.endsWith(suffix)) it.name.substring(0, it.name.length - suffix.length)
                    else it.name, it.returnType) }
                .forEach {
                    if (propMap.any { p -> p.name == it.first }) {
                        throw AssertionError("${it.first} duplicated between ${getProp(it.first)} and ${it.second}")
                    }
                    propMap.add(Prop(it.first, it.second, clazz))
                }
        }
    }

}