package io.github.inggameteam.api

import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import java.util.*

interface IngGamePlugin : Plugin, Listener {
    val console: UUID
    var allowTask: Boolean
    fun addDisableEvent(action: () -> Unit)
}