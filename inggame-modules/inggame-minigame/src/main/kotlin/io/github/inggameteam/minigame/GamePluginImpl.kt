package io.github.inggameteam.minigame

import io.github.inggameteam.party.PartyPluginImpl
import io.github.inggameteam.utils.IntVector

abstract class GamePluginImpl(
    val hubName: String,
    val worldName: String,
    val worldSize: IntVector,
    vararg init:  (GamePlugin, IntVector) -> Game,
    ) : GamePlugin, PartyPluginImpl() {
    override val gameSupplierRegister by lazy { GameSupplierRegister(this, *init) }
    override val gameRegister by lazy { GameRegister(this, hubName, worldName, worldSize) }
    override fun onEnable() {
        super.onEnable()
        gameSupplierRegister
        gameRegister
    }
}