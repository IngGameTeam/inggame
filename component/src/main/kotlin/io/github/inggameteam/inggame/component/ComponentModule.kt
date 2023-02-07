package io.github.inggameteam.inggame.component

import io.github.inggameteam.inggame.component.event.ComponentServiceRegisterEvent
import io.github.inggameteam.inggame.component.model.*
import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.component.wrapper.WrapperCodec
import io.github.inggameteam.inggame.component.wrapper.WrapperModel
import io.github.inggameteam.inggame.mongodb.DecodeFunction
import io.github.inggameteam.inggame.mongodb.EncodeFunction
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.Listener
import org.bukkit.event.EventHandler
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

class ComponentModule(val plugin: IngGamePlugin) : Listener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onRegisterComponentService(event: ComponentServiceRegisterEvent) {
        event.addModule(module(createdAtStart = true) {
//            factory {DecodeFunction { if (it is WrapperModel) it.createWrapper(getKoin()) else null } }
//            factory {EncodeFunction { if (it is Wrapper) WrapperModel(it) else null } }
        })
        event.register { "singleton" cs "default" }
        event.registerClass(
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