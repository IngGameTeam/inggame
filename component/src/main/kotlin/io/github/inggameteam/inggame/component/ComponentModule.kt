package io.github.inggameteam.inggame.component

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.event.IngGamePluginLoadEvent
import io.github.inggameteam.inggame.component.loader.Component
import io.github.inggameteam.inggame.component.model.*
import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.component.wrapper.WrapperCodec
import io.github.inggameteam.inggame.component.wrapper.WrapperModel
import io.github.inggameteam.inggame.mongodb.ClassRegistryAll
import io.github.inggameteam.inggame.mongodb.DecodeFunction
import io.github.inggameteam.inggame.mongodb.EncodeFunction
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.Listener
import org.bukkit.event.EventHandler
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.reflect.full.isSubclassOf

class ComponentModule(val plugin: IngGamePlugin) : Listener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onRegisterComponentService(event: IngGamePluginLoadEvent) {
        event.addModule(module(createdAtStart = true) {
            factory { DecodeFunction { if (it is WrapperModel) it.createWrapper(getKoin()) else null } }
            factory { EncodeFunction { if (it is Wrapper) WrapperModel(it) else null } }
            single {
                val handler = get<ComponentService>(named("handler"))
                val classes = get<ClassRegistryAll>()
                classes.classes
                    .filter { it.isSubclassOf(Handler::class) }
                    .forEach {
                        val name = it.simpleName!!
                        if (!handler.has(name, name)) {
                            handler.addNameSpace(name)
                            handler.set(name, name, true)
                        }
                    }
            }
        })
//        event.register {
//            this
//                .cs("singleton", isSavable = true)
//                .cs("handler", isSavable = true)
//                .cs("default")
//        }
        event.registerClass(
            Component::class,
            WrapperModel::class,
            WrapperCodec::class,
            Alert::class,
            ChatAlert::class,
            ActionBarAlert::class,
            EmptyAlert::class,
            TitleAlert::class,
            BaseComponentAlert::class,
            ActionComponent::class,
            LocationModel::class,
            InventoryModel::class,
            ItemModel::class,
        )
        event.addModule(
            module(createdAtStart = true) {
                singleOf(::SubClassRegistry)
                singleOf(::PropertyRegistry)

            })
    }
}