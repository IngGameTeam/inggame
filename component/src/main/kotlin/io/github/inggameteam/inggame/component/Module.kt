package io.github.inggameteam.inggame.component

import io.github.inggameteam.inggame.component.delegate.SimpleWrapper
import io.github.inggameteam.inggame.component.delegate.Wrapper
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

inline fun <reified T : Any> createSingleton(crossinline block: (Wrapper) -> T, nameSpace: Any, component: String) = module {
    single { block(SimpleWrapper(nameSpace, get(named(component)))) } bind T::class
}
