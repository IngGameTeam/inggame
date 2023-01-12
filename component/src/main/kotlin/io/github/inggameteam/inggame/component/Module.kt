package io.github.inggameteam.inggame.component

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentServiceImp
import io.github.inggameteam.inggame.component.componentservice.ResourcesComponentServiceImp
import io.github.inggameteam.inggame.component.delegate.Delegate
import io.github.inggameteam.inggame.component.delegate.SimpleDelegate
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

fun createResource(name: String, collection: String) = module {
    single(named(name)) { ResourcesComponentServiceImp(get(named(collection)), get()) } bind ComponentService::class
}

fun createLayer(collection: String, parentComponent: String) = module {
    single(named(collection)) { LayeredComponentServiceImp(get(named(collection)), get(), get(named(parentComponent))) } bind ComponentService::class
}

fun createSingleton(block: (Delegate) -> Delegate, nameSpace: Any, component: String) = module {
    single { block(SimpleDelegate(nameSpace, get(named(component)))) }
}