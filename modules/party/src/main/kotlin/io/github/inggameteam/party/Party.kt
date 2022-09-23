package io.github.inggameteam.party

import io.github.inggameteam.api.PluginHolder
import io.github.inggameteam.party.PartyAlert.*
import io.github.inggameteam.party.event.CreatePartyEvent
import io.github.inggameteam.party.event.JoinPartyEvent
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.GPlayerList
import io.github.inggameteam.player.eq
import io.github.inggameteam.player.toPlayerList
import io.github.inggameteam.utils.ColorUtil.color
import org.bukkit.Bukkit
import java.util.*

const val PARTY = "party"

class Party(
    override val plugin: PartyPlugin,
    val joined: GPlayerList = GPlayerList(),
    var opened: Boolean = true,
    var renamed: Boolean = false,
    val banList: ArrayList<UUID> = ArrayList(),
    ) : PluginHolder<PartyPlugin> {
    override fun toString() = name
    var leader: GPlayer
        get() = joined.first()
        set(value) {
            joined.remove(value)
            joined.add(0, value)
        }

    lateinit var name: String
    init {
        resetName()
    }
    val defaultName get() = "${leader}의 파티"
    fun resetName() {
        name = defaultName
        renamed = false
    }

}

val PluginHolder<PartyPlugin>.comp get() = plugin.partyComponent
fun PluginHolder<PartyPlugin>.updateParty() = plugin.partyUI.updateParty()

fun Party.disband(player: GPlayer) {
    if (leader == player) {
        comp.send(PARTY_DISBANDED, joined, this)
        plugin.partyRegister.remove(this)
        plugin.partyRequestRegister.removeIf { it.party == this }
        updateParty()
    } else {
        comp.send(PARTY_DISBAND_IS_LEADER_ONLY, player)
    }
}

fun Party.left(player: GPlayer) {
    if (leader == player) {
        disband(player)
    } else {
        joined.remove(player)
        comp.send(LEFT_PARTY, joined, player, this)
    }
}

fun Party.join(player: GPlayer) {
    if (joined.contains(player)) return

    if (plugin.partyRegister.joinedParty(player)) {
        plugin.partyRegister.getJoined(player)?.left(player)
    }
    joined.add(player)
    comp.send(JOIN_PARTY, joined, player, this)
    updateParty()
    Bukkit.getPluginManager().callEvent(JoinPartyEvent(player, this))
}

fun PartyRegister.createParty(dispatcher: GPlayer) {
    getJoined(dispatcher)?.left(dispatcher)
    add(Party(plugin, joined = listOf(dispatcher).toPlayerList()))

    comp.send(PARTY_CREATED, dispatcher)
    Bukkit.getPluginManager().callEvent(CreatePartyEvent(dispatcher))
}

fun Party.rename(dispatcher: GPlayer, newName: String) {
    if (leader eq dispatcher) {
        val beforeName = name
        if (newName.isEmpty()) {
            resetName()
        } else if (newName.length > 20) {
            comp.send(OVER_PARTY_NAME_LENGTH, dispatcher)
            return
        } else {
            name = newName.color
            renamed = true
        }
        comp.send(PARTY_RENAMED, joined, dispatcher, beforeName, name)
        updateParty()
    } else comp.send(PARTY_RENAME_IS_LEADER_ONLY, dispatcher)
}

fun Party.visible(dispatcher: GPlayer, newVisible: Boolean = !opened) = if (leader eq dispatcher) {
        opened = newVisible
        if (opened)
            comp.send(NOW_ANYONE_CAN_JOIN_PARTY, dispatcher)
        else
            comp.send(NOW_CANNOT_JOIN_WITHOUT_INVITE_PARTY, dispatcher)
    updateParty()
} else comp.send(PARTY_VISIBLE_IS_LEADER_ONLY, dispatcher)

fun Party.promote(dispatcher: GPlayer, newLeader: GPlayer) {
    if (leader eq dispatcher) {
        if (leader eq newLeader) comp.send(CANNOT_PROMOTE_YOURSELF, dispatcher)
        else if (joined.contains(newLeader)) {
            comp.send(LEADER_PROMOTE_YOU, newLeader, dispatcher, this)
            comp.send(PARTY_PROMOTED, joined.filter { it.uniqueId != newLeader.uniqueId }, newLeader, this)
            joined.remove(newLeader)
            joined.add(0, newLeader)
            if (!renamed) resetName()
            updateParty()
        } else {
            comp.send(PLAYER_NOT_EXIST_IN_PARTY, dispatcher)
        }
    } else comp.send(PARTY_PROMOTE_IS_LEADER_ONLY, dispatcher)
}

fun Party.kick(dispatcher: GPlayer, kickPlayer: GPlayer) {
    if (leader eq dispatcher) {
        if (leader eq kickPlayer) comp.send(CANNOT_KICK_YOURSELF, dispatcher)
        else if (joined.contains(kickPlayer)) {
            joined.remove(kickPlayer)
            comp.send(LEADER_KICKED_YOU, kickPlayer, leader, this)
            comp.send(PARTY_KICKED, joined, kickPlayer, this)
            plugin.partyRequestRegister.removeIf { it.party == this && it.sender == kickPlayer }
            updateParty()
        } else comp.send(PLAYER_NOT_EXIST_IN_PARTY, dispatcher)
    } else comp.send(PARTY_KICK_IS_LEADER_ONLY, dispatcher)
}

fun Party.ban(dispatcher: GPlayer, banPlayer: GPlayer) {
    if (leader eq dispatcher) {
        if (leader eq banPlayer) comp.send(CANNOT_BAN_YOURSELF, dispatcher)
        else if (joined.contains(banPlayer)) {
            joined.remove(banPlayer)
            banList.add(banPlayer.uniqueId)
            comp.send(LEADER_BANNED_YOU, banPlayer, leader, this)
            comp.send(YOU_BANNED_THE_PLAYER, dispatcher, this, banPlayer)
            plugin.partyRequestRegister.removeIf { it.party == this && it.sender == banPlayer }
            updateParty()
        } else comp.send(PLAYER_NOT_EXIST_IN_PARTY, dispatcher)
    } else comp.send(PARTY_BAN_IS_LEADER_ONLY, dispatcher)
}

fun Party.unban(dispatcher: GPlayer, unbanPlayer: GPlayer) {
    if (leader eq dispatcher) {
        if (leader eq unbanPlayer) comp.send(CANNOT_UNBAN_YOURSELF, dispatcher)
        else if (banList.contains(unbanPlayer.uniqueId)) {
            banList.remove(unbanPlayer.uniqueId)
            comp.send(PARTY_UNBANNED, joined, unbanPlayer, this)
        } else comp.send(THAT_PLAYER_WAS_NOT_BANNED, dispatcher)
    } else comp.send(PARTY_UNBAN_IS_LEADER_ONLY, dispatcher)
}

fun PartyRequestRegister.acceptInvitation(dispatcher: GPlayer, inviteCode: Int) {
    val requests = filter { it.receiver == dispatcher && inviteCode == it.code }.toList()
    removeAll(requests.toSet())
    if (requests.isEmpty()) comp.send(NO_PARTY_INVITATION, dispatcher)
    else requests.last().party.join(dispatcher)
}


fun PartyRequestRegister.inviteAll(sender: GPlayer) {
    if (plugin.partyRegister.joinedParty(sender)) {
        val party = plugin.partyRegister.getJoined(sender)!!
        Bukkit.getOnlinePlayers().map { plugin[it] }.filter {
            sender != it && !party.joined.contains(it) && !party.banList.contains(it.uniqueId)
        }.forEach{ receiver ->
            val request = PartyRequest(sender, receiver, party, Any().hashCode())
            comp.send(PARTY_REQUEST_TO_ALL, receiver, sender, party, request.code)
            add(request)
        }
        comp.send(SENT_PARTY_REQUEST_TO_ALL, sender, party)

        comp.send(SENT_PARTY_REQUEST_TO_ALL_RECEIVE_ALL, party.joined.filter { sender != it }, sender, party)
    }
}

fun PartyRequestRegister.invitePlayer(sender: GPlayer, receiver: GPlayer) {
    if (plugin.partyRegister.joinedParty(sender)) {
        val party = plugin.partyRegister.getJoined(sender)!!
        if (party.banList.contains(receiver.uniqueId)) {
            comp.send(CANNOT_REQUEST_PARTY_DUE_TO_BANNED, sender, receiver, party)
            return
        }
        val request = PartyRequest(sender, receiver, party, Any().hashCode())
        comp.send(PARTY_REQUEST, receiver, sender, party, request.code)
        comp.send(SENT_PARTY_REQUEST, sender, receiver, party)
        comp.send(SENT_PARTY_REQUEST_RECEIVE_ALL, party.joined.filter { sender != it }, sender, receiver, party)
        add(request)
    }
}

fun Party.listMembers(dispatcher: GPlayer) {
    dispatcher.sendMessage("-------------------------------------")
    dispatcher.sendMessage(
        "&a파티원(${joined.size}명): &6".color + joined,
    )
    dispatcher.sendMessage("-------------------------------------")
}
