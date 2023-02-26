package io.github.inggameteam.inggame.plugin

import io.github.inggameteam.inggame.component.*
import io.github.inggameteam.inggame.inggame.IngGamePluginImp
import io.github.inggameteam.inggame.minigame.GameModule
import io.github.inggameteam.inggame.minigame.view.GameViewModule
import io.github.inggameteam.inggame.party.PartyModule

@Suppress("unused")
class Plugin : IngGamePluginImp() {

    override fun onEnable() {
        super.onEnable()
        debugCommand(this, ingGame.app)
    }

    override fun registerModule() {
        GameModule(this)
        GameViewModule(this)
        PartyModule(this)
    }

}