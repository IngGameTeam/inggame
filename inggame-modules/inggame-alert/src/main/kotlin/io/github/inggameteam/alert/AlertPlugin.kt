package io.github.inggameteam.alert

import org.bukkit.plugin.Plugin
import java.util.*

interface AlertPlugin : Plugin {


    val component: Component
    fun alert(name: String) = component.alert[name]!!
    fun alert(enum: Enum<*>) = alert(enum.name)
    val console: UUID

}

