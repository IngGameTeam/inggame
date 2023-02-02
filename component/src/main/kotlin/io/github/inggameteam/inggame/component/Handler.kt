package io.github.inggameteam.inggame.component

import io.github.inggameteam.inggame.component.delegate.Wrapper
import kotlin.reflect.KClass

interface Handler {

    fun isHandler(wrapper: Wrapper): Boolean = wrapper.run { component[nameSpace, this@Handler.javaClass.simpleName, Boolean::class] }

    fun isNotHandler(wrapper: Wrapper) = !isHandler(wrapper)

    companion object {
        fun Wrapper.isHandler(handler: KClass<out Handler>) = component[nameSpace, handler.simpleName!!, Boolean::class]

        fun Wrapper.isNotHandler(handler: KClass<out Handler>) = !isHandler(handler)
    }

}