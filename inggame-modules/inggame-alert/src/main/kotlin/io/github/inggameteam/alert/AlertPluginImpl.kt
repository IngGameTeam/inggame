package io.github.inggameteam.alert

import io.github.inggameteam.utils.randomUUID
import org.bukkit.plugin.java.JavaPlugin

abstract class AlertPluginImpl : AlertPlugin {
    override val component by lazy { Component(dataFolder) }
    override val console by lazy { randomUUID() }
    override fun onEnable() { component;console }

}