package io.github.inggameteam.inggame.component

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.EmptyComponentServiceImp
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentServiceImp
import io.github.inggameteam.inggame.component.componentservice.ResourcesComponentServiceImp
import io.github.inggameteam.inggame.component.delegate.Delegate
import io.github.inggameteam.inggame.component.delegate.SimpleDelegate
import io.github.inggameteam.inggame.component.helper.AddToSaveRegistry
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import java.util.concurrent.atomic.AtomicInteger
import kotlin.reflect.KClass

class PriorityFactory {
    private val atomicInteger = AtomicInteger()
    fun get(): Int {
        return atomicInteger.getAndAdd(1)
    }
}

class PropRegistry(
    val models: ArrayList<KClass<*>>,
    val wrappers: ArrayList<KClass<*>>
)
fun createPropRegistry(models: ArrayList<KClass<*>>, wrappers: ArrayList<KClass<*>>) = module {
    single { PropRegistry(models, wrappers) }
}

fun createPriorityFactory() = module {
    single { PriorityFactory() } bind PriorityFactory::class
}

fun createEmpty(name: String) = module {
    single(named(name)) { EmptyComponentServiceImp() } bind ComponentService::class
}

fun createResource(name: String, parentComponent: String) = module {
    single(named(name)) { ResourcesComponentServiceImp(get(named(name)), get(), get(named(parentComponent)), get<PriorityFactory>().get()) } bind ComponentService::class
}

fun createLayer(collection: String, parentComponent: String) = module {
    single(named(collection)) { LayeredComponentServiceImp(get(named(collection)), get(), get(named(parentComponent)), get<PriorityFactory>().get()) } bind ComponentService::class
}

inline fun <reified T : Any> createSingleton(crossinline block: (Delegate) -> T, nameSpace: Any, component: String) = module {
    single { block(SimpleDelegate(nameSpace, get(named(component)))) } bind T::class
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> createSingleton(clazz: KClass<out T>, nameSpace: Any, component: String) = module {
    single { clazz.constructors.first().call(SimpleDelegate(nameSpace, get(named(component)))) } bind clazz as KClass<T>
}

fun addToSaveRegistry(component: String) = module {
    single(named(component)) { AddToSaveRegistry(get(named(component)), get()) }
}