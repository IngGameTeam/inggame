package io.github.inggameteam.minigame.angangang.game.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.angangang.game.base.SimpleGame
import io.github.inggameteam.minigame.base.CompetitionImpl
import io.github.inggameteam.minigame.base.SpawnPlayerRandomLocation

class RandomWeaponWar(plugin: GamePlugin) : CompetitionImpl(plugin),
    SpawnPlayerRandomLocation, SimpleGame {
    override val name get() = "random-weapon-war"
}