package io.github.inggameteam.minigame

enum class JoinType {
    PLAY, SPECTATE;
}

enum class LeftType(val isJoinHub: Boolean, val ignoreWinner: Boolean) {
    LEFT_SERVER(false, false),
    COMMAND(true, false),
    DUE_TO_MOVE_ANOTHER_GAME(false, false),
    GAME_STOP(true, false),
    SERVER_RELOAD(false, true),
}

enum class GameState {
    WAIT, PLAY, STOP;
}

