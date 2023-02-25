package io.github.inggameteam.inggame.utils

enum class JoinType {
    PLAY, SPECTATE;
}

enum class LeftType(val isJoinHub: Boolean) {
    LEFT_SERVER(false),
    COMMAND(true),
    DUE_TO_MOVE_ANOTHER(false),
    STOP(true),
    SERVER_RELOAD(false),
}

enum class ContainerState {
    WAIT, PLAY, STOP;

    override fun toString() = name
}

