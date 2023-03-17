package io.github.inggameteam.inggame.party.wrapper

import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.player.container.Container
import io.github.inggameteam.inggame.player.container.ContainerImp
import io.github.inggameteam.inggame.utils.SafeListWithToString
import java.util.*

interface Party : Wrapper, Container<PartyPlayer> {
    var isPartyOpened: Boolean
    val defaultName: String
    val partyBanList: SafeListWithToString<UUID>
    val renamed: Boolean
    var name: String
    var leader: PartyPlayer
    fun resetName()
}

class PartyImp(wrapper: Wrapper) : Party, ContainerImp<PartyPlayer>(wrapper) {
    override val containerName: String get() = name
    override var isPartyOpened: Boolean by default { true }
    private var renamedPartyName: String? by nullable
    override val partyBanList: SafeListWithToString<UUID> by default { SafeListWithToString<UUID>() }
    override val renamed: Boolean get() = renamedPartyName !== null
    override var name: String
        get() = renamedPartyName?: defaultName.format(leader)
        set(value) { renamedPartyName = value }
    override var leader: PartyPlayer by default { joinedPlayers.first() }
    override val defaultName: String by nonNull
    override fun resetName() { renamedPartyName = null }
    override fun toString() = name
}

