package io.github.inggameteam.alert

import io.github.inggameteam.alert.component.Component
import io.github.inggameteam.alert.component.ComponentImpl
import io.github.inggameteam.api.IngGamePluginImpl

abstract class AlertPluginImpl : AlertPlugin, IngGamePluginImpl() {
    override val components = HashMap<String, Component>()
    override val component by lazy { ComponentImpl(this, dataFolder) }
    override fun onEnable() {
        super.onEnable()
        components
        component
        dataFolder.listFiles { obj -> obj.isDirectory }?.forEach { file ->
            components[file.name] = ComponentImpl(this, file)
        }
    }
}