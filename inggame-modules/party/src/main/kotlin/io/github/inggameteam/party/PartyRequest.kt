package io.github.inggameteam.party

import io.github.inggameteam.player.GPlayer

class PartyRequest(
    val sender: GPlayer,
    val receiver: GPlayer,
    val party: Party,
    val code: Int
)