package io.github.inggameteam.inggame.party.wrapper

import io.github.inggameteam.inggame.component.model.Alert
import io.github.inggameteam.inggame.component.wrapper.Wrapper

interface PartyAlert : Wrapper {

    val PARTY_DISBANDED: Alert
    val PARTY_DISBAND_IS_LEADER_ONLY: Alert
    val LEFT_PARTY: Alert
    val OVER_PARTY_NAME_LENGTH: Alert
    val PARTY_RENAMED: Alert
    val PARTY_RENAME_IS_LEADER_ONLY: Alert
    val NOW_ANYONE_CAN_JOIN_PARTY: Alert
    val NOW_CANNOT_JOIN_WITHOUT_INVITE_PARTY: Alert
    val PARTY_VISIBLE_IS_LEADER_ONLY: Alert
    val CANNOT_PROMOTE_YOURSELF: Alert
    val LEADER_PROMOTE_YOU: Alert
    val PARTY_PROMOTED: Alert
    val PLAYER_NOT_EXIST_IN_PARTY: Alert
    val PARTY_PROMOTE_IS_LEADER_ONLY: Alert
    val CANNOT_KICK_YOURSELF: Alert
    val LEADER_KICKED_YOU: Alert
    val PARTY_KICKED: Alert
    val PARTY_KICK_IS_LEADER_ONLY: Alert
    val CANNOT_BAN_YOURSELF: Alert
    val LEADER_BANNED_YOU: Alert
    val YOU_BANNED_THE_PLAYER: Alert
    val PARTY_BAN_IS_LEADER_ONLY: Alert
    val CANNOT_UNBAN_YOURSELF: Alert
    val PARTY_UNBANNED: Alert
    val THAT_PLAYER_WAS_NOT_BANNED: Alert
    val PARTY_UNBAN_IS_LEADER_ONLY: Alert
    val PARTY_LIST_MEMBERS: Alert
    val PARTY_HELP: Alert
    val NO_PARTY_INVITATION: Alert
    val PARTY_REQUEST_TO_ALL: Alert
    val SENT_PARTY_REQUEST_TO_ALL: Alert
    val SENT_PARTY_REQUEST_TO_ALL_RECEIVE_ALL: Alert
    val CANNOT_REQUEST_PARTY_DUE_TO_BANNED: Alert
    val PARTY_REQUEST: Alert
    val SENT_PARTY_REQUEST: Alert
    val SENT_PARTY_REQUEST_RECEIVE_ALL: Alert
    val PLAYER_NOT_FOUND: Alert
    val PARTY_CREATED: Alert
}

class PartyAlertImp(wrapper: Wrapper) : Wrapper by wrapper, PartyAlert {
    override val PARTY_DISBANDED: Alert by nonNull
    override val PARTY_DISBAND_IS_LEADER_ONLY: Alert by nonNull
    override val LEFT_PARTY: Alert by nonNull
    override val OVER_PARTY_NAME_LENGTH: Alert by nonNull
    override val PARTY_RENAMED: Alert by nonNull
    override val PARTY_RENAME_IS_LEADER_ONLY: Alert by nonNull
    override val NOW_ANYONE_CAN_JOIN_PARTY: Alert by nonNull
    override val NOW_CANNOT_JOIN_WITHOUT_INVITE_PARTY: Alert by nonNull
    override val PARTY_VISIBLE_IS_LEADER_ONLY: Alert by nonNull
    override val CANNOT_PROMOTE_YOURSELF: Alert by nonNull
    override val LEADER_PROMOTE_YOU: Alert by nonNull
    override val PARTY_PROMOTED: Alert by nonNull
    override val PLAYER_NOT_EXIST_IN_PARTY: Alert by nonNull
    override val PARTY_PROMOTE_IS_LEADER_ONLY: Alert by nonNull
    override val CANNOT_KICK_YOURSELF: Alert by nonNull
    override val LEADER_KICKED_YOU: Alert by nonNull
    override val PARTY_KICKED: Alert by nonNull
    override val PARTY_KICK_IS_LEADER_ONLY: Alert by nonNull
    override val CANNOT_BAN_YOURSELF: Alert by nonNull
    override val LEADER_BANNED_YOU: Alert by nonNull
    override val YOU_BANNED_THE_PLAYER: Alert by nonNull
    override val PARTY_BAN_IS_LEADER_ONLY: Alert by nonNull
    override val CANNOT_UNBAN_YOURSELF: Alert by nonNull
    override val PARTY_UNBANNED: Alert by nonNull
    override val THAT_PLAYER_WAS_NOT_BANNED: Alert by nonNull
    override val PARTY_UNBAN_IS_LEADER_ONLY: Alert by nonNull
    override val PARTY_LIST_MEMBERS: Alert by nonNull
    override val PARTY_HELP: Alert by nonNull
    override val NO_PARTY_INVITATION: Alert by nonNull
    override val PARTY_REQUEST_TO_ALL: Alert by nonNull
    override val SENT_PARTY_REQUEST_TO_ALL: Alert by nonNull
    override val SENT_PARTY_REQUEST_TO_ALL_RECEIVE_ALL: Alert by nonNull
    override val CANNOT_REQUEST_PARTY_DUE_TO_BANNED: Alert by nonNull
    override val PARTY_REQUEST: Alert by nonNull
    override val SENT_PARTY_REQUEST: Alert by nonNull
    override val SENT_PARTY_REQUEST_RECEIVE_ALL: Alert by nonNull
    override val PLAYER_NOT_FOUND: Alert by nonNull
    override val PARTY_CREATED: Alert by nonNull
}