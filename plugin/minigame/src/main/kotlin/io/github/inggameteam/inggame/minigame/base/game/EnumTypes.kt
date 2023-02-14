package io.github.inggameteam.inggame.minigame.base.game;

enum class JoinType {
    PLAY, SPECTATE;
}

enum class LeftType(val isJoinHub: Boolean) {
    LEFT_SERVER(false),
    COMMAND(true),
    DUE_TO_MOVE_ANOTHER_GAME(false),
    GAME_STOP(true),
    SERVER_RELOAD(false),
}

enum class GameState {
    WAIT, PLAY, STOP;

    override fun toString() = name
}

