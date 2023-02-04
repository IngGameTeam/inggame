package io.github.inggameteam.inggame.component.delegate

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.NameSpaceNotFoundException
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService
import kotlin.reflect.KProperty

interface Wrapper {

    val nameSpace: Any

    val component: ComponentService

    val nonNull get() = NonNullWrapperImp(nameSpace, component)

    val nullable get() = NullableWrapperImp(nameSpace, component)

    fun addParents(value: Wrapper) = component.addParents(nameSpace, uncoverDelegate(value))

    fun getParents() = component.getParents(nameSpace)

    fun setParents(value: Collection<Wrapper>) = component.setParents(nameSpace, value.map(::uncoverDelegate))

    fun removeParents(value: Wrapper) = component.removeParents(nameSpace, uncoverDelegate(value))

    fun hasParents(value: Wrapper) = component.hasParents(nameSpace, uncoverDelegate(value))

    fun default(block: () -> Any) = NonNullWrapperImp(nameSpace, component).apply { defaultBlock = block }

    @Deprecated("deprecated", replaceWith = ReplaceWith("default { TODO() }"))
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

open class SimpleWrapper(

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
                component.find(nameSpace, property.name)
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
                component.find(nameSpace, property.name)
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

operator fun <T> ComponentService.get(nameSpace: Wrapper, block: (Wrapper) -> T): T {
    val ns = uncoverDelegate(nameSpace)
    return block(NonNullWrapperImp(ns, this))
}

operator fun <T> ComponentService.get(nameSpace: Any, block: (Wrapper) -> T): T {
    val ns = uncoverDelegate(nameSpace)
    return block(NonNullWrapperImp(ns, this))
}

inline fun <reified T : Any> LayeredComponentService.getAll(noinline block: (Wrapper) -> T): Collection<T> {
    return this.getAll().map(NameSpace::name).map { get(it, block) }
}

operator fun <T> Wrapper.get(block: (Wrapper) -> T): T {
    return block(NonNullWrapperImp(nameSpace, component))
}

fun <T : Any> uncoverDelegate(any: T): Any {
    return any

}

fun <T : Wrapper> uncoverDelegate(any: T): Any {
    return any.nameSpace
}