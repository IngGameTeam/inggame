package io.github.inggameteam.inggame.component

import io.github.inggameteam.inggame.component.event.ComponentServiceRegisterEvent
import io.github.inggameteam.inggame.component.model.*
import io.github.inggameteam.inggame.utils.ClassRegistry
import io.github.inggameteam.inggame.utils.HandleListener
import io.github.inggameteam.inggame.utils.fastToString
import io.github.inggameteam.inggame.utils.randomUUID
import org.bukkit.event.EventHandler
import org.bukkit.plugin.Plugin
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

class ComponentModule(plugin: Plugin) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onRegisterComponentService(event: ComponentServiceRegisterEvent) {
        event.register { "singleton" cs "default" }
        event.registerClass(
                        Alert::class,
                        ChatAlert::class,
                        EmptyAlert::class,
                        ActionBarAlert::class,
                        TitleAlert::class,
                        BaseComponentAlert::class,
                        ActionComponent::class,
                        Location::class,
                        InventoryModel::class,
                        ItemModel::class,
        ),
            module(createdAtStart = true) {
                singleOf(::SubClassRegistry)
                singleOf(::PropertyRegistry)

            })
    }
}