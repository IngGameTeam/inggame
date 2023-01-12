package io.github.inggameteam.inggame.component

import io.github.inggameteam.inggame.component.componentservice.*
import io.github.inggameteam.inggame.component.delegate.Delegate
import io.github.inggameteam.inggame.component.delegate.SimpleDelegate
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.primaryConstructor

fun createEmpty(name: String) = module {
    single(named(name)) { EmptyComponentServiceImp() } bind ComponentService::class
}
fun createResource(name: String, parentComponent: String) = module {
    single(named(name)) { ResourcesComponentServiceImp(get(named(name)), get(), get(named(parentComponent))) } bind ComponentService::class
}

fun createLayer(collection: String, parentComponent: String) = module {
    single(named(collection)) { LayeredComponentServiceImp(get(named(collection)), get(), get(named(parentComponent))) } bind ComponentService::class
}

inline fun <reified T : Any> createSingleton(crossinline block: (Delegate) -> T, nameSpace: Any, component: String) = module {
    single { block(SimpleDelegate(nameSpace, get(named(component)))) } bind T::class
}

inline fun <reified T : Any> createSingleton(clazz: KClass<out T>, nameSpace: Any, component: String) = module {
    single { clazz.constructors.first().call(SimpleDelegate(nameSpace, get(named(component)))) } bind clazz as KClass<T>
}