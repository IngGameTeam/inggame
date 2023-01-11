package io.github.inggameteam.inggame.minigame.wrapper.game

import io.github.inggameteam.inggame.component.delegate.Delegate
import java.util.*

class Game(delegate: Delegate) : Delegate by delegate {

    val startPlayersAmount      : Int           by nonNull
    val playerLimitAmount       : Int           by nonNull
    val startWaitingSecond      : Int           by nonNull
    val stopWaitingTick         : Int           by nonNull
    var joined                  : HashSet<UUID> by nonNull


}
