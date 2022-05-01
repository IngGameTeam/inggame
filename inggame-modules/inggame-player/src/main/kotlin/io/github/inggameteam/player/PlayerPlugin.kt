package io.github.inggameteam.player

import io.github.inggameteam.api.IngGamePlugin

interface PlayerPlugin : IngGamePlugin {

    val playerRegister: GPlayerRegister
}