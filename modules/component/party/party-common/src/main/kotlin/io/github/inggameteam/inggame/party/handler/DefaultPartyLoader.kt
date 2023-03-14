package io.github.inggameteam.inggame.party.handler

import io.github.inggameteam.inggame.party.wrapper.PartyServer
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.Listener

class DefaultPartyLoader(
    partyServer: PartyServer,
    partyHelper: PartyHelper,
    plugin: IngGamePlugin) : Listener(plugin) {

    init {
        partyServer.defaultParty = partyHelper.createContainer()
    }



}