package io.github.inggameteam.alert

import io.github.inggameteam.api.IngGamePlugin
import org.bukkit.plugin.Plugin
import java.util.*

interface AlertPlugin : Plugin, IngGamePlugin {


    val component: Component
    fun alert(name: String) = component.alert[name]!!
    fun alert(enum: Enum<*>) = alert(enum.name)

}

