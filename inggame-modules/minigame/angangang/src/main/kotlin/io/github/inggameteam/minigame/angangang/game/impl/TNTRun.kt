package io.github.inggameteam.minigame.angangang.game.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.angangang.game.base.SimpleGame
import io.github.inggameteam.minigame.base.ClearTheBlocksBelow
import io.github.inggameteam.minigame.base.CompetitionImpl

class TNTRun(plugin: GamePlugin) : SimpleGame, CompetitionImpl(plugin), ClearTheBlocksBelow {
    override val name get() = "tnt-run"



}