package io.github.inggameteam.inggame.component

import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
annotation class Subs(
    vararg val kClasses: KClass<*>
)
