package io.github.inggameteam.inggame.minigame.wrapper.game

import io.github.inggameteam.inggame.component.delegate.Delegate

class VoidDeath(delegate: Delegate) : Delegate by delegate {

    val voidDeath: Int by nonNull

}