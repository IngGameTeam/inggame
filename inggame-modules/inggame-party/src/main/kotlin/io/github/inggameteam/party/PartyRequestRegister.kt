package io.github.inggameteam.party

import io.github.inggameteam.player.GPlayer
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import io.github.inggameteam.party.PartyAlert.*
import io.github.inggameteam.utils.ColorUtil.color

class PartyRequestRegister(val plugin: PartyPlugin) : HashSet<PartyRequest>(), Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    fun left(event: PlayerQuitEvent) {
        val player = plugin[event.player]
        removeIf { it.receiver == player || it.sender == player }
    }

    fun request(sender: GPlayer, receiver: GPlayer) {
        val partyRegister = plugin.partyRegister
        if (partyRegister.joinedParty(sender)) {
            val party = partyRegister.getJoined(sender)!!
            if (party.banList.contains(receiver.uniqueId)) {
                plugin.component.send(CANNOT_REQUEST_PARTY_DUE_TO_BANNED, sender, receiver, party)
                return
            }
            val request = PartyRequest(sender, receiver, party, Any().hashCode())
            plugin.component.send(PARTY_REQUEST, receiver, sender, party, request.code)
            plugin.component.send(SENT_PARTY_REQUEST, sender, receiver, party)
            plugin.component.send(SENT_PARTY_REQUEST_RECEIVE_ALL, party.joined.filter { sender != it }, sender, receiver, party)
            add(request)
        }
    }

    fun requestAll(sender: GPlayer) {
        val partyRegister = plugin.partyRegister
        if (partyRegister.joinedParty(sender)) {
            val party = partyRegister.getJoined(sender)!!
            Bukkit.getOnlinePlayers().map { plugin[it] }.filter {
                sender != it && !party.joined.contains(it) && !party.banList.contains(it.uniqueId)
            }.forEach{ receiver ->
                val request = PartyRequest(sender, receiver, party, Any().hashCode())
                plugin.component.send(PARTY_REQUEST_TO_ALL, receiver, sender, party, request.code)
                add(request)
            }
            plugin.component.send(SENT_PARTY_REQUEST_TO_ALL, sender, party)

            plugin.component.send(SENT_PARTY_REQUEST_TO_ALL_RECEIVE_ALL, party.joined.filter { sender != it }, sender, party)
        }
    }

    fun accept(acceptor: GPlayer, code: Int) {
        val requests = stream().filter { it.receiver == acceptor && code == it.code }.toList()
        removeAll(requests.toSet())
        if (requests.isEmpty()) acceptor.sendMessage("&c파티 초대가 없습니다".color)
        else requests.last().party.join(acceptor)
    }



}
