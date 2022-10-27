package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.player.hasTags

interface ScaleRedTeam : TeamCompetition {
    val scale get() = 4
    override fun generateHalfSize() = (joined.hasTags(PTag.PLAY).size / scale.toDouble()).toInt().plus(1)
}