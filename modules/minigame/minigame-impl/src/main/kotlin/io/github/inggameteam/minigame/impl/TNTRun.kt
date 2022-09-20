package io.github.inggameteam.minigame.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.base.*

class TNTRun(plugin: GamePlugin) : SimpleGame, CompetitionImpl(plugin),
    ClearTheBlocksBelow, NoBlockBreak, NoBlockPlace {
    override val name get() = "tnt-run"



}