package io.github.inggameteam.inggame.component

import io.github.inggameteam.inggame.component.componentservice.*
import io.github.inggameteam.inggame.component.delegate.Wrapper
import io.github.inggameteam.inggame.component.delegate.SimpleWrapper
import io.github.inggameteam.inggame.component.helper.AddToSaveRegistry
import io.github.inggameteam.inggame.component.model.*
import io.github.inggameteam.inggame.component.view.EditorRegistry
import io.github.inggameteam.inggame.utils.ClassRegistry
import io.github.inggameteam.inggame.utils.fastToString
import io.github.inggameteam.inggame.utils.randomUUID
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import kotlin.reflect.KClass

fun registerComponentModels() = module {
    factory(named(randomUUID().fastToString())) {
        ClassRegistry(
            Alert::class,
            ChatAlert::class,
            ActionBarAlert::class,
            TitleAlert::class,
            BaseComponentAlert::class,
            ActionComponent::class,
            Location::class,
            InventoryModel::class,
            ItemModel::class,
        )
    }
}

fun createSubClassRegistry() = module(createdAtStart = true) {
    singleOf(::SubClassRegistry)
}

fun createEditorRegistry() = module(createdAtStart = true) {
    singleOf(::EditorRegistry)
}

fun createPropertyRegistry() = module(createdAtStart = true) {
    singleOf(::PropertyRegistry)
}

fun createEmpty(name: String) = module {
    single(named(name)) { EmptyComponentServiceImp(name) } bind ComponentService::class
}

fun createResource(name: String, parentComponent: String) = module {
    single(named(name)) { ResourceComponentServiceImp(get(named(name)), get(), get(named(parentComponent)), name) } bind ComponentService::class
}

fun createLayer(collection: String, parentComponent: String) = module {
    single(named(collection)) { LayeredComponentServiceImp(get(named(collection)), get(), get(named(parentComponent)), collection) } bind ComponentService::class
}

fun createMultiParents(name: String, rootComponent: String?, components: Collection<String>, key: String?) = module {
    single(named(name)) { MultiParentsComponentService(name,
        { rootComponent?.run { get(named(this)) } }, components.map { get(named(it)) }, key)} bind ComponentService::class
}


inline fun <reified T : Any> createSingleton(crossinline block: (Wrapper) -> T, nameSpace: Any, component: String) = module {
    single { block(SimpleWrapper(nameSpace, get(named(component)))) } bind T::class
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> createSingleton(clazz: KClass<out T>, nameSpace: Any, component: String) = module {
    single { clazz.constructors.first().call(SimpleWrapper(nameSpace, get(named(component)))) } bind clazz as KClass<T>
}

fun addToSaveRegistry(component: String) = module(createdAtStart = true) {
    single(named(component)) { AddToSaveRegistry(get(named(component)), get()) }
}