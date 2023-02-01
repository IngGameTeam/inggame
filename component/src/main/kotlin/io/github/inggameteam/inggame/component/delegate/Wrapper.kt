package io.github.inggameteam.inggame.component.delegate

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.NameSpaceNotFoundException
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService
import java.util.*
import kotlin.reflect.KProperty
import kotlin.reflect.full.isSubclassOf

interface Wrapper {

    val nameSpace: Any

    val component: ComponentService

    val nonNull get() = NonNullWrapperImp(nameSpace, component)

    val nullable get() = NullableWrapperImp(nameSpace, component)

    fun addParents(value: Any) = component.addParents(nameSpace, value)

    fun getParents() = component.getParents(nameSpace)

    fun setParents(value: Collection<Any>) = component.setParents(nameSpace, value)

    fun removeParents(value: Any) = component.removeParents(nameSpace, value)

    fun hasParents(value: Any) = component.hasParents(nameSpace, value)

    fun default(block: () -> Any) = NonNullWrapperImp(nameSpace, component).apply { defaultBlock = block }

    fun nullableDefault(block: () -> Any?) = NullableWrapperImp(nameSpace, component).apply { defaultBlock = block }


}

abstract class BaseWrapper : Wrapper {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (this.nameSpace == other) return true
        if (other is Wrapper && this.nameSpace == other.nameSpace) return true
        return false
    }

    override fun hashCode(): Int {
        return nameSpace.hashCode()
    }

}

class SimpleWrapper(

    override val nameSpace: Any,

    override val component: ComponentService
) : BaseWrapper() {
    constructor(wrapper: Wrapper) : this(wrapper.nameSpace, wrapper.component)
}

class NullableWrapperImp(

    override val nameSpace: Any,

    override val component: ComponentService,
) : BaseWrapper() {


    var defaultBlock: (() -> Any?)? = null

    inline operator fun <T : Any, reified R> getValue(thisRef: T, property: KProperty<*>): R? {
        try {
            val result = try {
                component[nameSpace, property.name, Any::class]
            } catch (e: Throwable) {
                val defaultValue = defaultBlock?.invoke()?.apply { setValue(thisRef, property, this) }
                if (defaultValue === null) return null
                defaultValue
            }
            return result as R
        } catch (e: NameSpaceNotFoundException) {
            throw AssertionError("'$nameSpace' name space '${property.name}' key '${thisRef.javaClass.simpleName}' ref not exist")
        }
    }

    operator fun <T, R : Any> setValue(thisRef: T, property: KProperty<*>, value: R?) {
        if (value === null) {
            component[nameSpace].elements.remove(property.name)
        } else {
            component.set(nameSpace, property.name, value)
        }
    }

}

class NonNullWrapperImp(

    override val nameSpace: Any,

    override val component: ComponentService,
) : BaseWrapper() {


    var defaultBlock: (() -> Any)? = null

    inline operator fun <T : Any, reified R> getValue(thisRef: T, property: KProperty<*>): R {
        try {
            val result = try {
                component[nameSpace, property.name, Any::class]
            } catch (e: Throwable) {
                val defaultValue = defaultBlock?.invoke()?.apply { setValue(thisRef, property, this) }
                if (defaultValue === null) throw e
                defaultValue
            }
            return result as R
        } catch (e: NameSpaceNotFoundException) {
            throw AssertionError("'$nameSpace' name space '${property.name}' key '${thisRef.javaClass.simpleName}' ref not exist")
        }
    }

    operator fun <T, R : Any> setValue(thisRef: T, property: KProperty<*>, value: R) {
        component.set(nameSpace, property.name, value)
    }


}

fun <T> ComponentService.get(nameSpace: Any, block: (Wrapper) -> T): T {
    val ns = uncoverDelegate(nameSpace)
    return block(NonNullWrapperImp(ns, this))
}

fun <T> LayeredComponentService.getAll(block: (Wrapper) -> T): Collection<T> {
    return this.getAll().map(NameSpace::name).map { get(it, block) }
}

operator fun <T> Wrapper.get(block: (Wrapper) -> T): T {
    return block(NonNullWrapperImp(nameSpace, component))
}

fun <T : Any> uncoverDelegate(any: T): Any {
    return if (any is Wrapper) any.nameSpace else any
}