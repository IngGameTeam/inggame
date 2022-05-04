package io.github.inggameteam.plugin.party

import io.github.inggameteam.party.PartyPluginImpl

class PartyPlugin : PartyPluginImpl() {

    override fun onEnable() {
        super.onEnable()
        println(partyComponent.string.comp("enable_plugin"))
    }
}