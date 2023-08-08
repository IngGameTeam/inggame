package io.github.inggameteam.party

enum class PartyAlert {
    PARTY_CREATED,
    PARTY_DISBANDED,
    JOIN_PARTY,
    LEFT_PARTY,
    PARTY_RENAMED,
    PARTY_UNBANNED,
    PARTY_PROMOTED,
    PARTY_KICKED,
    PARTY_BANNED,
    CANNOT_REQUEST_PARTY_DUE_TO_BANNED,
    PARTY_REQUEST,
    SENT_PARTY_REQUEST,
    SENT_PARTY_REQUEST_RECEIVE_ALL,
    PARTY_REQUEST_TO_ALL,
    SENT_PARTY_REQUEST_TO_ALL,
    SENT_PARTY_REQUEST_TO_ALL_RECEIVE_ALL,


    OVER_PARTY_NAME_LENGTH,
    PARTY_DISBAND_IS_LEADER_ONLY,
    PARTY_RENAME_IS_LEADER_ONLY,
    PARTY_VISIBLE_IS_LEADER_ONLY,
    PARTY_PROMOTE_IS_LEADER_ONLY,
    PARTY_BAN_IS_LEADER_ONLY,
    PARTY_UNBAN_IS_LEADER_ONLY,
    PARTY_KICK_IS_LEADER_ONLY,


    JOINED_PARTY_NOT_EXIST,
    NOW_ANYONE_CAN_JOIN_PARTY,
    NOW_CANNOT_JOIN_WITHOUT_INVITE_PARTY,
    CANNOT_PROMOTE_YOURSELF,
    PLAYER_NOT_EXIST_IN_PARTY,
    LEADER_PROMOTE_YOU,

    CANNOT_KICK_YOURSELF,
    LEADER_KICKED_YOU,

    CANNOT_BAN_YOURSELF,
    LEADER_BANNED_YOU,
    YOU_BANNED_THE_PLAYER,

    CANNOT_UNBAN_YOURSELF,
    THAT_PLAYER_WAS_NOT_BANNED,

    NO_PARTY_INVITATION,

    CANNOT_JOIN_DUE_TO_BANNED,

    PARTY_HELP,







    ;

    override fun toString() = name

}