package io.github.inggameteam.inggame.minigame.wrapper.game

import io.github.inggameteam.inggame.component.delegate.Delegate
import io.github.inggameteam.inggame.component.model.Alert

class GameAlert(delegate: Delegate) : Delegate by delegate {

    val ALREADY_JOINED: Alert by nonNull
    val CANNOT_JOIN_DUE_TO_STARTED: Alert by nonNull
    val CANNOT_JOIN_PLAYER_LIMITED: Alert by nonNull
    val JOIN: Alert by nonNull
    val START_SPECTATING: Alert by nonNull
    val LEFT_GAME_DUE_TO_SERVER_LEFT: Alert by nonNull
    val LEFT: Alert by nonNull
    val START_CANCELLED_DUE_TO_PLAYERLESS: Alert by nonNull
    val GAME_START_COUNT_DOWN: Alert by nonNull
    val GAME_START: Alert by nonNull


}