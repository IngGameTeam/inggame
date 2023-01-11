package io.github.inggameteam.inggame.minigame.wrapper.game

import io.github.inggameteam.inggame.component.delegate.Delegate

class RewardPoint(delegate: Delegate) : Delegate by delegate {

    val rewardPoint: Int by nonNull


}