package io.github.inggameteam.api

import org.bukkit.plugin.Plugin

interface PluginHolder<PLUGIN : Plugin> {

    val plugin: PLUGIN

}