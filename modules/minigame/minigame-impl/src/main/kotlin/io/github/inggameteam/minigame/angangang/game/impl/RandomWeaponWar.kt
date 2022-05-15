package io.github.inggameteam.minigame.angangang.game.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.base.SimpleGame
import io.github.inggameteam.base.CompetitionImpl
import io.github.inggameteam.base.SpawnPlayerRandomLocation

class RandomWeaponWar(plugin: GamePlugin) : CompetitionImpl(plugin),
    SpawnPlayerRandomLocation, SimpleGame {
    override val name get() = "random-weapon-war"
}