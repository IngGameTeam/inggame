package io.github.inggameteam.inggame.component.delegate

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.NameSpaceNotFoundException
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService
import java.util.*
import java.util.concurrent.CopyOnWriteArraySet
import kotlin.reflect.KProperty

interface Delegate {
    val nameSpace: Any
    val component: ComponentService

    fun addParents(value: Any) = component.addParents(nameSpace, value)

    fun getParents() = component.getParents(nameSpace)

    fun setParents(value: Collection<Any>) = component.setParents(nameSpace, value)

    fun removeParents(value: Any) = component.removeParents(nameSpace, value)

    fun hasParents(value: Any) = component.hasParents(nameSpace, value)

    fun default(block: () -> Any) = NonNullDelegateImp(nameSpace, component).apply { defaultBlock = block }

    fun nullableDefault(block: () -> Any?) = NullableDelegateImp(nameSpace, component).apply { defaultBlock = block }

    val nonNull get() = NonNullDelegateImp(nameSpace, component)

    val nullable get() = NullableDelegateImp(nameSpace, component)

}
interface NonNullDelegate : Delegate {

    operator fun <T: Any, R> getValue(thisRef: T, property: KProperty<*>): R
    operator fun <T, R : Any> setValue(thisRef: T, property: KProperty<*>, value: R)

}

interface NullableDelegate : Delegate {

    operator fun <T: Any, R> getValue(thisRef: T, property: KProperty<*>): R?
    operator fun <T, R : Any> setValue(thisRef: T, property: KProperty<*>, value: R?)

}

abstract class BaseDelegate : Delegate {

    override fun hashCode(): Int {
        return nameSpace.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this.nameSpace.equals(other)) return true
        return false
    }


}

class SimpleDelegate(override val nameSpace: Any, override val component: ComponentService) : BaseDelegate()

class NullableDelegateImp(
    override val nameSpace: Any,
    override val component: ComponentService,
) : NullableDelegate, BaseDelegate() {

    internal var defaultBlock: (() -> Any?)? = null

    @Suppress("UNCHECKED_CAST")
    override operator fun <T : Any, R> getValue(thisRef: T, property: KProperty<*>): R? {
        val result = try {
            component[nameSpace, property.name, Any::class]
        } catch (_: Throwable) {
            defaultBlock?.invoke()?.apply { setValue(thisRef, property, this) }
        }
        return result as? R
    }

    override operator fun <T, R : Any> setValue(thisRef: T, property: KProperty<*>, value: R?) {
        if (value === null) {
            component[nameSpace].elements.remove(property.name)
        } else {
            component.set(nameSpace, property.name, value)
        }
    }

}

class NonNullDelegateImp(
    override val nameSpace: Any,
    override val component: ComponentService,
) : NonNullDelegate, BaseDelegate() {

    var defaultBlock: (() -> Any)? = null

    @Suppress("UNCHECKED_CAST")
    override operator fun <T : Any, R> getValue(thisRef: T, property: KProperty<*>): R {
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

    override operator fun <T, R : Any> setValue(thisRef: T, property: KProperty<*>, value: R) {
        component.set(nameSpace, property.name, value)
    }


}

fun <T> ComponentService.get(nameSpace: Any, block: (Delegate) -> T): T {
    val ns = if (nameSpace is Delegate) nameSpace.nameSpace else nameSpace
    return block(NonNullDelegateImp(ns, this))
}

fun <T> LayeredComponentService.getAll(block: (Delegate) -> T): Collection<T> {
    return this.getAll().map(NameSpace::name).map { get(it, block) }
}

operator fun <T> Delegate.get(block: (Delegate) -> T): T {
    return block(NonNullDelegateImp(nameSpace, component))
}