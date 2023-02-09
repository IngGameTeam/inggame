package io.github.inggameteam.inggame.component

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.wrapper.SimpleWrapper
import io.github.inggameteam.inggame.component.wrapper.Wrapper
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

inline fun <reified T : Any> createSingleton(crossinline block: (Wrapper) -> T, nameSpace: Any, component: String) = module {
    single {
        val componentService = get<ComponentService>(named(component))
        componentService.addNameSpace(nameSpace)
        block(SimpleWrapper(nameSpace, componentService))
    } bind T::class
}
