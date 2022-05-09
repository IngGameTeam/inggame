package io.github.inggameteam.minigame.angangang.game.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.Sector
import io.github.inggameteam.minigame.angangang.game.base.SimpleGame
import io.github.inggameteam.minigame.base.ClearTheBlocksBelow
import io.github.inggameteam.minigame.base.CompetitionImpl

class TNTRun(plugin: GamePlugin, point: Sector) : SimpleGame, CompetitionImpl(plugin, point), ClearTheBlocksBelow {
    override val name get() = "tnt-run"



}