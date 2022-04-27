package io.github.inggameteam.party

import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.GPlayerList
import java.util.*
import kotlin.collections.ArrayList

interface Party {

    fun join(player: GPlayer)
    fun left(player: GPlayer)
    fun resetName()
    var leader: GPlayer
    val joined: GPlayerList
    var name: String
    var renamed: Boolean
    var opened: Boolean
    val banList: ArrayList<UUID>

    companion object {
        const val PARTY = "PARTY"
    }

}