package io.github.inggameteam.inggame.plugin

import io.github.inggameteam.inggame.component.*
import io.github.inggameteam.inggame.inggame.IngGamePluginImp

@Suppress("unused")
class Plugin : IngGamePluginImp() {

    override fun onEnable() {
        super.onEnable()
        debugCommand(this, ingGame.app)
    }

    override fun registerModule() {
//        GameModule(this)
//        GameViewModule(this)
//        PartyModule(this)
//        PartyViewModule(this)
    }

}