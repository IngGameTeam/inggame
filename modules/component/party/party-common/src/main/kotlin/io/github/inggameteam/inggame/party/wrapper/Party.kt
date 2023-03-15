package io.github.inggameteam.inggame.party.wrapper

import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.player.container.ContainerImp
import io.github.inggameteam.inggame.utils.SafeListWithToString
import java.util.*

class Party(wrapper: Wrapper) : ContainerImp<PartyPlayer>(wrapper) {
    var isPartyOpened: Boolean by default { true }
    private var renamedPartyName: String? by nullable
    val partyBanList: SafeListWithToString<UUID> by default { SafeListWithToString<UUID>() }
    val renamed: Boolean get() = renamedPartyName !== null
    var name: String
        get() = renamedPartyName?: defaultName.format(leader)
        set(value) { renamedPartyName = value }
    var leader: PartyPlayer by nonNull
    val defaultName: String by default { "%s's party" }
    fun resetName() {
        renamedPartyName = null
    }
    override fun toString() = name
}

