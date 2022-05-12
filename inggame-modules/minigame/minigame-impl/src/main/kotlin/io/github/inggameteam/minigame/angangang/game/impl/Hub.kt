package io.github.inggameteam.minigame.angangang.game.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.angangang.game.base.*
import io.github.inggameteam.minigame.angangang.game.base.Hub
import io.github.inggameteam.minigame.ui.MinigameMenu

class Hub(plugin: GamePlugin) : Hub(plugin), SpawnPlayer, SpawnOnJoin, VoidDeath, SpawnHealth,
        ParticleOnJoin, MinigameMenu
