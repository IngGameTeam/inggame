package io.github.inggameteam.api

import io.github.inggameteam.utils.randomUUID
import org.bukkit.plugin.java.JavaPlugin

abstract class IngGamePluginImpl : IngGamePlugin, JavaPlugin() {
    override val console = randomUUID()
    override var allowTask = false
    override fun onEnable() {
        super.onEnable()
        allowTask = true
        console
    }

    override fun onDisable() {
        super.onDisable()
        allowTask = false
    }
}