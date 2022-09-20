package io.github.inggameteam.minigame.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.base.*

class PushGame(plugin: GamePlugin) : CompetitionImpl(plugin), SimpleGame, SpawnPlayer, NoItemDrop, NoItemPickup {
    override val name get() = "push-game"
}