package io.github.inggameteam.inggame.component.view

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KType
import kotlin.reflect.jvm.javaType

val KType.singleClass: Class<*>
    get() {
        val javaType = javaType
        return if (javaType is Class<out Any>) {
            javaType
        } else if (javaType is ParameterizedType) {
            javaType.rawType as Class<out Any>
        } else throw AssertionError("cannot read class type")
    }
