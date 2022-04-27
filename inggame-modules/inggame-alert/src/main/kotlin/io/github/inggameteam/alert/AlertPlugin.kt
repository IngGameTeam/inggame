package io.github.inggameteam.alert

import org.bukkit.plugin.Plugin
import java.util.*

interface AlertPlugin : Plugin {


    val alertComponent: AlertComponent
    fun alert(name: String) = alertComponent.alert[name]!!
    fun alert(enum: Enum<*>) = alert(enum.name)
    val console: UUID

}

