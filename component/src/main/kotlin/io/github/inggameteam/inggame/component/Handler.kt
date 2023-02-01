package io.github.inggameteam.inggame.component

import io.github.inggameteam.inggame.component.Handler.Companion.isHandler
import io.github.inggameteam.inggame.component.delegate.Wrapper
import kotlin.reflect.KClass

interface Handler {

    fun isHandler(wrapper: Wrapper): Boolean = wrapper.run { component.hasParents(nameSpace, javaClass.simpleName) }

    fun isNotHandler(wrapper: Wrapper) = !isHandler(wrapper)

    companion object {
        fun Wrapper.isHandler(handler: KClass<out Handler>) = component.hasParents(nameSpace, handler.simpleName!!)

        fun Wrapper.isNotHandler(handler: KClass<out Handler>) = !isHandler(handler)
    }

}