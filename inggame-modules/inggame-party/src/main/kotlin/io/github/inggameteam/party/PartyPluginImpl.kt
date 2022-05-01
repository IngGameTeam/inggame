package io.github.inggameteam.party

import io.github.inggameteam.player.PlayerPluginImpl

abstract class PartyPluginImpl : PartyPlugin, PlayerPluginImpl() {
    override val partyRegister by lazy { PartyRegister(this) }
    override val partyRequestRegister by lazy { PartyRequestRegister(this) }
    override val partyUI by lazy { PartyUI(this) }
}