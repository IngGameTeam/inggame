package io.github.inggameteam.minigame

import io.github.inggameteam.party.PartyPlugin

interface GamePlugin : PartyPlugin {

    val gameRegister: GameRegister
    val gameSupplierRegister: GameSupplierRegister


}