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

    override fun hashCode(): Int {
        return nameSpace.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return this.nameSpace == other
    }


}

class SimpleWrapper(

    override val nameSpace: Any,

    override val component: ComponentService
) : BaseWrapper()

class NullableWrapperImp(

    override val nameSpace: Any,

    override val component: ComponentService,
) : BaseWrapper() {


    internal var defaultBlock: (() -> Any?)? = null

    @Suppress("UNCHECKED_CAST")
    operator fun <T : Any, R> getValue(thisRef: T, property: KProperty<*>): R? {
        val result = try {
            component[nameSpace, property.name, Any::class]
        } catch (_: Throwable) {
            defaultBlock?.invoke()
        }
        return result as? R
    }

    operator fun <T, R : Any> setValue(thisRef: T, property: KProperty<*>, value: R?) {
        if (value === null) {
            component[nameSpace].elements.remove(property.name)
        } else {
            component.set(nameSpace, property.name, uncoverDelegate(value))
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
                val defaultValue = defaultBlock?.invoke()
                if (defaultValue === null) throw e
                defaultValue
            }
            try {
                return if (R::class.isSubclassOf(Wrapper::class))
                    R::class.constructors.first().call(NonNullWrapperImp(result, component))
                else result as R
            } catch (_: Throwable) {
                throw AssertionError("an error occurred while wrap property due to non exist consturctor")
            }

        } catch (e: NameSpaceNotFoundException) {
            throw AssertionError("'$nameSpace' name space '${property.name}' key '${thisRef.javaClass.simpleName}' ref not exist")
        }
    }

    operator fun <T, R : Any> setValue(thisRef: T, property: KProperty<*>, value: R) {
        component.set(nameSpace, property.name, uncoverDelegate(value))
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

fun uncoverDelegate(any: Any): Any {
    return if (any is Wrapper) any.nameSpace else any
}