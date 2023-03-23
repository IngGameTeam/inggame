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
    var partyNameOrNull: String?
    var partyName: String
        get() = partyNameOrNull!!
        set(value) { partyNameOrNull = value }
    var leader: PartyPlayer
    fun resetName()
}

class PartyImp(wrapper: Wrapper) : Party, ContainerImp<PartyPlayer>(wrapper) {
    override val containerName: String get() = partyNameOrNull!!
    override var isPartyOpened: Boolean by default { true }
    override val partyBanList: SafeListWithToString<UUID> by default { SafeListWithToString<UUID>() }
    override var partyNameOrNull: String? by nullableDefault { defaultName }
    override var leader: PartyPlayer by default { joinedPlayers.first() }
    override val defaultName: String get() = get(::defaultName.name, String::class).format(leader)
    override fun resetName() { partyNameOrNull = null }
    override fun toString() = partyNameOrNull!!
}

