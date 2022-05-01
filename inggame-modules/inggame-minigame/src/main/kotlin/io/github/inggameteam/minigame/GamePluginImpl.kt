package io.github.inggameteam.minigame

import io.github.inggameteam.party.PartyPluginImpl
import io.github.inggameteam.utils.IntVector

abstract class GamePluginImpl(
    val hubName: String,
    val worldName: String,
    val worldSize: IntVector,
    vararg init:  (IntVector) -> Game,
    ) : GamePlugin, PartyPluginImpl() {
    /*

     */
    override val gameRegister by lazy { GameRegister(this, hubName, worldName, worldSize) }
    override val gameSupplierRegister by lazy { GameSupplierRegister(*init) }
}