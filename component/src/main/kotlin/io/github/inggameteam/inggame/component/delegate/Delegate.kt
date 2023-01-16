package io.github.inggameteam.inggame.component.delegate

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.NameSpaceNotFoundException
import io.github.inggameteam.inggame.component.NonElement
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService
import java.util.*
import kotlin.reflect.KProperty
import kotlin.reflect.full.isSubclassOf

interface Delegate {
    @NonElement
    val nameSpace: Any
    @NonElement
    val component: ComponentService
    @NonElement
    val nonNull get() = NonNullDelegateImp(nameSpace, component)
    @NonElement
    val nullable get() = NullableDelegateImp(nameSpace, component)

    fun addParents(value: Any) = component.addParents(nameSpace, value)

    fun getParents() = component.getParents(nameSpace)

    fun setParents(value: Collection<Any>) = component.setParents(nameSpace, value)

    fun removeParents(value: Any) = component.removeParents(nameSpace, value)

    fun hasParents(value: Any) = component.hasParents(nameSpace, value)

    fun default(block: () -> Any) = NonNullDelegateImp(nameSpace, component).apply { defaultBlock = block }

    fun nullableDefault(block: () -> Any?) = NullableDelegateImp(nameSpace, component).apply { defaultBlock = block }


}

abstract class BaseDelegate : Delegate {

    override fun hashCode(): Int {
        return nameSpace.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return this.nameSpace == other
    }


}

class SimpleDelegate(override val nameSpace: Any, override val component: ComponentService) : BaseDelegate()

class NullableDelegateImp(
    @NonElement
    override val nameSpace: Any,
    @NonElement
    override val component: ComponentService,
) : BaseDelegate() {

    @NonElement
    internal var defaultBlock: (() -> Any?)? = null

    @Suppress("UNCHECKED_CAST")
    operator fun <T : Any, R> getValue(thisRef: T, property: KProperty<*>): R? {
        val result = try {
            component[nameSpace, property.name, Any::class]
        } catch (_: Throwable) {
            defaultBlock?.invoke()?.apply { setValue(thisRef, property, this) }
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

class NonNullDelegateImp(
    @NonElement
    override val nameSpace: Any,
    @NonElement
    override val component: ComponentService,
) : BaseDelegate() {

    @NonElement
    var defaultBlock: (() -> Any)? = null

    @Suppress("UNCHECKED_CAST")
    inline operator fun <T : Any, reified R> getValue(thisRef: T, property: KProperty<*>): R {
        try {
            val result = try {
                component[nameSpace, property.name, Any::class]
            } catch (e: Throwable) {
                val defaultValue = defaultBlock?.invoke()?.apply { setValue(thisRef, property, this) }
                if (defaultValue === null) throw e
                defaultValue
            }
            return if (R::class.isSubclassOf(Delegate::class))
                R::class.constructors.first().call(NonNullDelegateImp(result, component))
            else
                result as R
        } catch (e: NameSpaceNotFoundException) {
            throw AssertionError("'$nameSpace' name space '${property.name}' key '${thisRef.javaClass.simpleName}' ref not exist")
        }
    }

    operator fun <T, R : Any> setValue(thisRef: T, property: KProperty<*>, value: R) {
        component.set(nameSpace, property.name, uncoverDelegate(value))
    }


}

fun <T> ComponentService.get(nameSpace: Any, block: (Delegate) -> T): T {
    val ns = uncoverDelegate(nameSpace)
    return block(NonNullDelegateImp(ns, this))
}

fun <T> LayeredComponentService.getAll(block: (Delegate) -> T): Collection<T> {
    return this.getAll().map(NameSpace::name).map { get(it, block) }
}

operator fun <T> Delegate.get(block: (Delegate) -> T): T {
    return block(NonNullDelegateImp(nameSpace, component))
}

fun uncoverDelegate(any: Any): Any {
    return if (any is Delegate) any.nameSpace else any
}