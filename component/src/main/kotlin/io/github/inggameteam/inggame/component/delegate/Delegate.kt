package io.github.inggameteam.inggame.component.delegate

import io.github.inggameteam.inggame.component.NameSpaceNotFoundException
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import java.util.*
import java.util.concurrent.CopyOnWriteArraySet
import kotlin.reflect.KProperty

interface Delegate {
    val parents: CopyOnWriteArraySet<Any>
    val nameSpace: Any
    val component: ComponentService

    fun default(block: () -> Any) = NonNullDelegateImp(nameSpace, component).apply { defaultBlock = block }

    fun nullableDefault(block: () -> Any?) = NullableDelegateImp(nameSpace, component).apply { defaultBlock = block }

    val nonNull get() = NonNullDelegateImp(nameSpace, component)

    val nullable get() = NullableDelegateImp(nameSpace, component)

}
interface NonNullDelegate : Delegate {

    operator fun <T, R> getValue(thisRef: T, property: KProperty<*>): R
    operator fun <T, R : Any> setValue(thisRef: T, property: KProperty<*>, value: R)

}

interface NullableDelegate : Delegate {

    operator fun <T, R> getValue(thisRef: T, property: KProperty<*>): R?
    operator fun <T, R : Any> setValue(thisRef: T, property: KProperty<*>, value: R?)

}

abstract class BaseDelegate : Delegate {
    override val parents get() = component.getParents(nameSpace)
}

class SimpleDelegate(override val nameSpace: Any, override val component: ComponentService) : BaseDelegate()

class NullableDelegateImp(
    override val nameSpace: Any,
    override val component: ComponentService,
) : NullableDelegate, BaseDelegate() {

    internal var defaultBlock: (() -> Any?)? = null

    @Suppress("UNCHECKED_CAST")
    override operator fun <T, R> getValue(thisRef: T, property: KProperty<*>): R? {
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
            component[nameSpace].elements[property.name] = value
        }
    }

}

class NonNullDelegateImp(
    override val nameSpace: Any,
    override val component: ComponentService,
) : NonNullDelegate, BaseDelegate() {

    var defaultBlock: (() -> Any)? = null

    @Suppress("UNCHECKED_CAST")
    override operator fun <T, R> getValue(thisRef: T, property: KProperty<*>): R {
        val result = try {
            component[nameSpace, property.name, Any::class]
        } catch (e: Throwable) {
            defaultBlock?.invoke()?.apply { setValue(thisRef, property, this) } ?: throw e
        }
        return result as R
    }

    override operator fun <T, R : Any> setValue(thisRef: T, property: KProperty<*>, value: R) {
        component[nameSpace].elements[property.name] = value
    }


}

fun <T> ComponentService.get(uuid: UUID, block: (Delegate) -> T): T {
    return block(NonNullDelegateImp(uuid, this))
}