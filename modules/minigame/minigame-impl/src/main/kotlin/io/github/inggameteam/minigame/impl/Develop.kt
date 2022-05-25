package io.github.inggameteam.minigame.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.base.SectionalImpl
import io.github.inggameteam.minigame.base.SpawnOnJoin
import io.github.inggameteam.minigame.base.SpawnPlayer

class Develop(plugin: GamePlugin) : SectionalImpl(plugin), SpawnPlayer, SpawnOnJoin {
    override val name get() = "develop-game"
    override val recommendedStartPlayersAmount get() = -1

}