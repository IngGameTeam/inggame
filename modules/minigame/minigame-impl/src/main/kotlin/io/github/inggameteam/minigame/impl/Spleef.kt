package io.github.inggameteam.minigame.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.base.CompetitionImpl
import io.github.inggameteam.minigame.base.PreventFallDamage
import io.github.inggameteam.minigame.base.SimpleGame
import io.github.inggameteam.minigame.base.SpawnPlayer

class Spleef(plugin: GamePlugin) : SimpleGame, CompetitionImpl(plugin), PreventFallDamage, SpawnPlayer {
    override val name get() = "spleef"

}