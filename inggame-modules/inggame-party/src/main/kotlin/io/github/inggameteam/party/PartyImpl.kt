package io.github.inggameteam.party

import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.GPlayerList
import io.github.inggameteam.party.PartyAlert.*
import java.util.*

class PartyImpl(
    val plugin: PartyPlugin,
    gPlayer: Array<GPlayer>,
    override var opened: Boolean = true,
    override var renamed: Boolean = false,
    val isSilent: Boolean = false,
    override val banList: ArrayList<UUID> = ArrayList(),
) : Party {
    override val joined = GPlayerList()
    override lateinit var name: String

    val comp get() = plugin.component

    init {
        joined.addAll(gPlayer)
        if (!isSilent) comp.send(PARTY_CREATED, joined, this)
        resetName()
    }

    override fun resetName() {
        name = "${leader}의 파티"
        renamed = false
    }

    override var leader: GPlayer
        get() = joined.first()
        set(value) {
            joined.remove(value)
            joined.add(0, value)
        }
    override fun join(player: GPlayer) {
        if (joined.contains(player)) return
        if (plugin.partyRegister.joinedParty(player)) {
            plugin.partyRegister.getJoined(player)?.left(player)
        }
        joined.add(player)
        comp.send(JOIN_PARTY, joined, player, this)
    }

    override fun left(player: GPlayer) {
        if (leader == player) {

            comp.send(PARTY_DISBANDED, joined, this)
            plugin.partyRegister.remove(this)
            joined.clear()
            plugin.partyUI.updateParty()
            return
        }
        joined.remove(player)
        comp.send(LEFT_PARTY, joined, player, this)

    }

    override fun toString() = name

}