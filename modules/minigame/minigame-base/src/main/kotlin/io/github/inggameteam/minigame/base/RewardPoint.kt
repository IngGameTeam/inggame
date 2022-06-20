package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.player.GPlayer

interface RewardPoint : Game {

    fun rewardPoint(player: GPlayer): Int = 100

}