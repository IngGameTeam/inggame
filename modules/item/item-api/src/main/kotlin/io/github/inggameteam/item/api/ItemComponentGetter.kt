package io.github.inggameteam.item.api

import io.github.inggameteam.alert.AlertPlugin
import io.github.inggameteam.api.PluginHolder

interface ItemComponentGetter : PluginHolder<AlertPlugin> {
    val itemComp get() = plugin.components["item"]
}