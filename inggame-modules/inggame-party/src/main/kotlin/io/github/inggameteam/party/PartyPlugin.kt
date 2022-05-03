package io.github.inggameteam.party

import io.github.inggameteam.alert.AlertPlugin
import io.github.inggameteam.player.PlayerPlugin

interface PartyPlugin : AlertPlugin {

    val partyRegister: PartyRegister
    val partyRequestRegister: PartyRequestRegister
    val partyUI: PartyUI
}