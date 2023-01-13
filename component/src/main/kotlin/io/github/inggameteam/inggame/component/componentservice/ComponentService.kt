package io.github.inggameteam.inggame.component.componentservice

import io.github.inggameteam.inggame.component.NameSpace
import java.util.concurrent.CopyOnWriteArraySet
import kotlin.reflect.KClass

interface ComponentService {

    operator fun <T : Any> get(nameSpace: Any, key: Any, clazz: KClass<T>): T
    fun has(nameSpace: Any, key: Any): Boolean

    fun set(nameSpace: Any, key: Any, value: Any? = null)

    fun setParents(name: Any, value: Collection<Any>)
    fun getParents(name: Any): CopyOnWriteArraySet<Any>
    fun addParents(name: Any, parents: Any)
    fun removeParents(name: Any, parents: Any)
    fun hasParents(name: Any, parents: Any): Boolean

    fun newModel(name: Any): NameSpace
    operator fun get(name: Any): NameSpace
    fun getOrNull(name: Any): NameSpace?



}