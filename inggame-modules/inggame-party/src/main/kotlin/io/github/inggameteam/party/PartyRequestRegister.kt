package io.github.inggameteam.party

import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.game
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import io.github.inggameteam.party.PartyAlert.*
import io.github.inggameteam.player.GPlayerList
import io.github.inggameteam.utils.ColorUtil.color

class PartyRequestRegister(val plugin: PartyPlugin) : HashSet<PartyRequest>(), Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    fun left(event: PlayerQuitEvent) {
        val player = event.player.game
        removeIf { it.receiver == player || it.sender == player }
    }

    fun request(sender: GPlayer, receiver: GPlayer) {
        val partyRegister = plugin.partyRegister
        if (partyRegister.joinedParty(sender)) {
            val party = partyRegister.getJoined(sender)!!
            if (party.banList.contains(receiver.player.uniqueId)) {
                plugin.alert(CANNOT_REQUEST_PARTY_DUE_TO_BANNED).send(plugin.console, sender.player, receiver, party)
                return
            }
            val request = PartyRequest(sender, receiver, party, Any().hashCode())
            plugin.alert(PARTY_REQUEST).send(plugin.console, receiver.player, sender, party, request.code)
            plugin.alert(SENT_PARTY_REQUEST).send(plugin.console, sender.player, receiver, party)
            GPlayerList(party.joined.filter { sender != it })
                .receiveAll(plugin.console, plugin.alert(SENT_PARTY_REQUEST_RECEIVE_ALL), sender, receiver, party)
            add(request)
        }
    }

    fun requestAll(sender: GPlayer) {
        val partyRegister = plugin.partyRegister
        if (partyRegister.joinedParty(sender)) {
            val party = partyRegister.getJoined(sender)!!
            Bukkit.getOnlinePlayers().map { it.game }.filter {
                sender != it && !party.joined.contains(it) && !party.banList.contains(it.player.uniqueId)
            }.forEach{ receiver ->
                val request = PartyRequest(sender, receiver, party, Any().hashCode())
                plugin.alert(PARTY_REQUEST_TO_ALL).send(plugin.console, receiver.player, sender, party, request.code)
                add(request)
            }
            plugin.alert(SENT_PARTY_REQUEST_TO_ALL).send(plugin.console, sender.player, party)
            GPlayerList(party.joined.filter { sender != it })
                .receiveAll(plugin.console, plugin.alert(SENT_PARTY_REQUEST_TO_ALL_RECEIVE_ALL), sender, party)
        }
    }

    fun accept(acceptor: GPlayer, code: Int) {
        val requests = stream().filter { it.receiver == acceptor && code == it.code }.toList()
        removeAll(requests)
        if (requests.isEmpty()) acceptor.player.sendMessage("&c파티 초대가 없습니다".color)
        else requests.last().party.join(acceptor)
    }



}
