package io.github.inggameteam.inggame.minigame.base

import io.github.inggameteam.inggame.component.delegate.Wrapper

class RewardPoint(wrapper: Wrapper) : Wrapper by wrapper {

    val rewardPoint: Int by nonNull


}