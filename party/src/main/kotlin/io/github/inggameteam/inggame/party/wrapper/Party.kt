package io.github.inggameteam.inggame.party.wrapper

import io.github.inggameteam.inggame.component.delegate.Delegate
import io.github.inggameteam.inggame.player.warpper.WrappedPlayer
import java.util.*
import kotlin.collections.ArrayList

class Party(delegate: Delegate) : Delegate by delegate {
    val partyJoined: ArrayList<WrappedPlayer> by default { ArrayList<WrappedPlayer>() }
    var isPartyOpened: Boolean by default { true }
    private var renamedPartyName: String? by nullable
    val partyBanList: ArrayList<UUID> by default { ArrayList<UUID>() }

    val renamed get() = renamedPartyName !== null
    var name: String
        get() = renamedPartyName?: defaultName
        set(value) { renamedPartyName = value }
    override fun toString() = name
    var leader: WrappedPlayer
        get() = partyJoined.first()
        set(value) {
            partyJoined.remove(value)
            partyJoined.add(0, value)
        }
    private val defaultName get() = "${leader}의 파티"
    fun resetName() {
        renamedPartyName = null
    }

}

