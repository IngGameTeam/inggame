package io.github.inggameteam.inggame.minigame.wrapper.game

import io.github.inggameteam.inggame.component.delegate.Delegate

class SpawnPlayer(delegate: Delegate) : Delegate by delegate {

    val gameMode: Int by nonNull

}