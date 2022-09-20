package io.github.inggameteam.plugin.cheesesand.game

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.base.*
import io.github.inggameteam.minigame.base.Hub

class Hub(plugin: GamePlugin) : Hub(plugin),
    SpawnPlayer, SpawnOnJoin, VoidDeath, SpawnHealth, ClearPotionOnJoin, NoItemDrop {
        override val name get() = plugin.gameRegister.hubName
}