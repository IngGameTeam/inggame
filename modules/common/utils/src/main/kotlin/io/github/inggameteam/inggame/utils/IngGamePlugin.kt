package io.github.inggameteam.inggame.utils

import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import java.util.*

interface IngGamePlugin : Plugin, Listener {
    val console: UUID
    var allowTask: Boolean
    val isMockTest: Boolean
    fun addDisableEvent(action: () -> Unit)
    fun addSaveEvent(action: () -> Unit)
    fun registerModule()
}