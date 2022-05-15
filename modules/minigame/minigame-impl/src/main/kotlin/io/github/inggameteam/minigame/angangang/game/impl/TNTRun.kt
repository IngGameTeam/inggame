package io.github.inggameteam.minigame.angangang.game.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.base.SimpleGame
import io.github.inggameteam.base.ClearTheBlocksBelow
import io.github.inggameteam.base.CompetitionImpl

class TNTRun(plugin: GamePlugin) : SimpleGame, CompetitionImpl(plugin), ClearTheBlocksBelow {
    override val name get() = "tnt-run"



}