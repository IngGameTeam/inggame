package io.github.inggameteam.party

import io.github.inggameteam.alert.AlertPlugin
import kotlin.test.assertNotNull

interface PartyPlugin : AlertPlugin {

    val partyRegister: PartyRegister
    val partyRequestRegister: PartyRequestRegister
    val partyUI: PartyUI
    val partyComponent get() = components[PARTY].apply { assertNotNull(this, "party component does not exist") }!!
}