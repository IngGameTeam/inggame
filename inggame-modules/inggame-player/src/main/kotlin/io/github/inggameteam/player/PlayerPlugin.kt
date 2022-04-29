package io.github.inggameteam.player

import org.bukkit.plugin.Plugin

interface PlayerPlugin : Plugin {

    val playerRegister: GPlayerRegister

}