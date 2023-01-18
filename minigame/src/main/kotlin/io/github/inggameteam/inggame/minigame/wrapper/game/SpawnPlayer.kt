package io.github.inggameteam.inggame.minigame.wrapper.game

import io.github.inggameteam.inggame.component.delegate.Wrapper

class SpawnPlayer(wrapper: Wrapper) : Wrapper by wrapper {

    val gameMode: Int by nonNull

}