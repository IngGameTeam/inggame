package io.github.inggameteam.player

abstract class PlayerPluginImpl : PlayerPlugin {
    override val playerRegister by lazy { GPlayerRegister(this) }
}