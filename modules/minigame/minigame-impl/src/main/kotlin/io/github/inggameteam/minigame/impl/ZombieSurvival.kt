package io.github.inggameteam.minigame.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.base.InfectionImpl
import io.github.inggameteam.minigame.base.SimpleGame
import io.github.inggameteam.minigame.base.SpawnTeamPlayer

class ZombieSurvival(plugin: GamePlugin) : InfectionImpl(plugin), SimpleGame, SpawnTeamPlayer {
    override val name get() = "zombie-survival"
}