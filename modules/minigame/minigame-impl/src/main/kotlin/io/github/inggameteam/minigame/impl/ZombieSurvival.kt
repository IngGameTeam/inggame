package io.github.inggameteam.minigame.impl

import io.github.inggameteam.bossbar.GBar
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.base.BarGame
import io.github.inggameteam.minigame.base.InfectionImpl
import io.github.inggameteam.minigame.base.SimpleGame
import io.github.inggameteam.minigame.base.SpawnTeamPlayer

class ZombieSurvival(plugin: GamePlugin) : InfectionImpl(plugin), SimpleGame, SpawnTeamPlayer, BarGame {
    override val name get() = "zombie-survival"
    override val bar by lazy { GBar(plugin) }
}