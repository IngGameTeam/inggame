package io.github.inggameteam.inggame.component

import io.github.inggameteam.inggame.component.event.ComponentServiceRegisterEvent
import io.github.inggameteam.inggame.component.model.*
import io.github.inggameteam.inggame.utils.HandleListener
import org.bukkit.event.EventHandler
import org.bukkit.plugin.Plugin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

class ComponentModule(plugin: Plugin) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onRegisterComponentService(event: ComponentServiceRegisterEvent) {
        event.register { "singleton" cs "default" }
        event.registerClass(
            Alert::class,
            ChatAlert::class,
            ActionBarAlert::class,
            EmptyAlert::class,
            TitleAlert::class,
            BaseComponentAlert::class,
            ActionComponent::class,
            Location::class,
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