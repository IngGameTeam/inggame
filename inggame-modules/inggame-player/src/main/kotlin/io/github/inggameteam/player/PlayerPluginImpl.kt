package io.github.inggameteam.player

import io.github.inggameteam.alert.AlertPluginImpl

abstract class PlayerPluginImpl : PlayerPlugin, AlertPluginImpl() {
    override val playerRegister by lazy { GPlayerRegister(this) }
    override fun onEnable() {
        playerRegister
    }
}