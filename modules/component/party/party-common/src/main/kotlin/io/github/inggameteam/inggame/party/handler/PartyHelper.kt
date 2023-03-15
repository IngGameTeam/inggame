package io.github.inggameteam.inggame.party.handler

import io.github.inggameteam.inggame.party.component.PartyInstanceService
import io.github.inggameteam.inggame.party.component.PartyPlayerService
import io.github.inggameteam.inggame.party.component.PartyRequestInstanceRepo
import io.github.inggameteam.inggame.party.event.JoinPartyEvent
import io.github.inggameteam.inggame.party.event.PartyDisbandEvent
import io.github.inggameteam.inggame.party.event.PartyLeftEvent
import io.github.inggameteam.inggame.party.event.PartyUpdateEvent
import io.github.inggameteam.inggame.party.wrapper.*
import io.github.inggameteam.inggame.player.container.ContainerHelperBase
import io.github.inggameteam.inggame.utils.*
import io.github.inggameteam.inggame.utils.ColorUtil.color
import org.bukkit.Bukkit

class PartyHelper(
    val plugin: IngGamePlugin,
    private val partyPlayerService: PartyPlayerService,
    private val partyInstanceService: PartyInstanceService,
    private val partyRequestInstanceRepo: PartyRequestInstanceRepo,
    private val partyServer: PartyServer
) : ContainerHelperBase<Party, PartyPlayer>(partyInstanceService, partyInstanceService, {partyServer.defaultParty}) {

    fun createContainer(parent: String = "party"): Party {
        return super.createContainer(parent, partyInstanceService[randomUUID(), ::Party])
    }

    override fun removeContainer(container: Party) {
        Bukkit.getPluginManager().callEvent(PartyDisbandEvent(container))
        super.removeContainer(container)
    }

    override fun onJoin(container: Party, element: PartyPlayer, joinType: JoinType) {
        Bukkit.getPluginManager().callEvent(JoinPartyEvent(element, container))
    }

    override fun onLeft(element: PartyPlayer, container: Party, leftType: LeftType) {
        Bukkit.getPluginManager().callEvent(PartyLeftEvent(element, container))
    }

    fun createParty(dispatcher: PartyPlayer) {
        leftContainer(dispatcher, LeftType.DUE_TO_MOVE_ANOTHER)
        joinContainer(createContainer(), dispatcher)
    }

    fun renameParty(dispatcher: PartyPlayer, newName: String): Unit = dispatcher.joined.run {
        if (leader == dispatcher) {
            val beforeName = name
            if (newName.isEmpty()) {
                resetName()
            } else if (newName.length > 20) {
                dispatcher[::PartyAlertImp].OVER_PARTY_NAME_LENGTH.send(dispatcher)
                return
            } else {
                name = newName.color()
            }
            dispatcher[::PartyAlertImp].PARTY_RENAMED.send(dispatcher, beforeName, name)
            Bukkit.getPluginManager().callEvent(PartyUpdateEvent(this))
        } else dispatcher[::PartyAlertImp].PARTY_RENAME_IS_LEADER_ONLY.send(dispatcher)
    }

    fun visible(dispatcher: PartyPlayer, newVisible: Boolean = !dispatcher.joined.isPartyOpened) =
         dispatcher.joined.run {
        if (leader == dispatcher) {
            isPartyOpened = newVisible
            if (isPartyOpened)
                dispatcher[::PartyAlertImp].NOW_ANYONE_CAN_JOIN_PARTY.send(dispatcher)
            else
                dispatcher[::PartyAlertImp].NOW_CANNOT_JOIN_WITHOUT_INVITE_PARTY.send(dispatcher)
            Bukkit.getPluginManager().callEvent(PartyUpdateEvent(this))
        } else {
            dispatcher[::PartyAlertImp].PARTY_VISIBLE_IS_LEADER_ONLY.send(dispatcher)
        }
    }

    fun assertPlayer(dispatcher: PartyPlayer, name: String, block: (PartyPlayer) -> Unit) {
        val player = Bukkit.getPlayer(name)?.run { partyPlayerService[uniqueId, ::PartyPlayerImp] }
        if (player !== null) block(player)
        else dispatcher[::PartyAlertImp].PLAYER_NOT_FOUND.send(dispatcher, name)
    }

    fun promote(dispatcher: PartyPlayer, newLeader: PartyPlayer): Unit = dispatcher.joined.run {
        if (leader == dispatcher) {
            if (leader == newLeader) dispatcher[::PartyAlertImp].CANNOT_PROMOTE_YOURSELF.send(dispatcher)
            else if (joinedPlayers.contains(newLeader)) {
                dispatcher[::PartyAlertImp].LEADER_PROMOTE_YOU.send(newLeader, dispatcher, this)
                joinedPlayers.filter { it != newLeader }.fastForEach { p ->
                    p[::PartyAlertImp].PARTY_PROMOTED.send(p, newLeader, this)
                }
                leader = newLeader
                if (!renamed) resetName()
                Bukkit.getPluginManager().callEvent(PartyUpdateEvent(this))
            } else dispatcher[::PartyAlertImp].PLAYER_NOT_EXIST_IN_PARTY.send(dispatcher)
        } else dispatcher[::PartyAlertImp].PARTY_PROMOTE_IS_LEADER_ONLY.send(dispatcher)
    }

    fun kick(dispatcher: PartyPlayer, kickPlayer: PartyPlayer): Unit = dispatcher.joined.run {
        if (leader == dispatcher) {
            if (leader == kickPlayer) dispatcher[::PartyAlertImp].CANNOT_KICK_YOURSELF.send(dispatcher)
            else if (joinedPlayers.contains(kickPlayer)) {
                joinedPlayers.remove(kickPlayer)
                partyRequestInstanceRepo.removePlayer(kickPlayer)
                dispatcher[::PartyAlertImp].LEADER_KICKED_YOU.send(kickPlayer, leader, this)
                joinedPlayers.forEach { p -> p[::PartyAlertImp].PARTY_KICKED.send(p, kickPlayer, this) }
                partyRequestInstanceRepo.removeIf { it.partyRequestedParty == this }
                partyRequestInstanceRepo.removePlayer(kickPlayer)
                Bukkit.getPluginManager().callEvent(PartyUpdateEvent(this))
            } else dispatcher[::PartyAlertImp].PLAYER_NOT_EXIST_IN_PARTY.send(dispatcher)
        } else dispatcher[::PartyAlertImp].PARTY_KICK_IS_LEADER_ONLY.send(dispatcher)
    }

    fun ban(dispatcher: PartyPlayer, banPlayer: PartyPlayer): Unit = dispatcher.joined.run {
        if (leader == dispatcher) {
            if (leader == banPlayer) dispatcher[::PartyAlertImp].CANNOT_BAN_YOURSELF.send(dispatcher)
            else if (joinedPlayers.contains(banPlayer)) {
                joinedPlayers.remove(banPlayer)
                partyRequestInstanceRepo.removePlayer(banPlayer)
                partyBanList.add(banPlayer.uniqueId)
                dispatcher[::PartyAlertImp].LEADER_BANNED_YOU.send(banPlayer, leader, this)
                dispatcher[::PartyAlertImp].YOU_BANNED_THE_PLAYER.send(dispatcher, this, banPlayer)
                partyRequestInstanceRepo.removeIf { it.partyRequestedParty == this }
                Bukkit.getPluginManager().callEvent(PartyUpdateEvent(this))
            } else dispatcher[::PartyAlertImp].PLAYER_NOT_EXIST_IN_PARTY.send(dispatcher)
        } else dispatcher[::PartyAlertImp].PARTY_BAN_IS_LEADER_ONLY.send(dispatcher)
    }

    fun unban(dispatcher: PartyPlayer, unbanPlayer: PartyPlayer): Unit = dispatcher.joined.run {
        if (leader == dispatcher) {
            if (leader == unbanPlayer) dispatcher[::PartyAlertImp].CANNOT_UNBAN_YOURSELF.send(dispatcher)
            else if (partyBanList.contains(unbanPlayer.uniqueId)) {
                partyBanList.remove(unbanPlayer.uniqueId)
                joinedPlayers.forEach { p -> p[::PartyAlertImp].PARTY_UNBANNED.send(p, unbanPlayer, this) }
            } else dispatcher[::PartyAlertImp].THAT_PLAYER_WAS_NOT_BANNED.send(dispatcher)
        } else dispatcher[::PartyAlertImp].PARTY_UNBAN_IS_LEADER_ONLY.send(dispatcher)
    }

    fun listMembers(dispatcher: PartyPlayer): Unit = dispatcher.joined.run {
        dispatcher[::PartyAlertImp].PARTY_LIST_MEMBERS.send(dispatcher, joinedPlayers.size, joinedPlayers)
    }

    fun partyHelp(dispatcher: PartyPlayer) {
        dispatcher[::PartyAlertImp].PARTY_HELP.send(dispatcher)
    }

}