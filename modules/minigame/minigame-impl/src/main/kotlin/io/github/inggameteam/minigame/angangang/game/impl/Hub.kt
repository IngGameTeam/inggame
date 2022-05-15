package io.github.inggameteam.minigame.angangang.game.impl

import io.github.inggameteam.base.*
import io.github.inggameteam.base.Hub
import io.github.inggameteam.minigame.GamePlugin

class Hub(plugin: GamePlugin) : Hub(plugin), SpawnPlayer, SpawnOnJoin, VoidDeath, SpawnHealth,
    ParticleOnJoin
