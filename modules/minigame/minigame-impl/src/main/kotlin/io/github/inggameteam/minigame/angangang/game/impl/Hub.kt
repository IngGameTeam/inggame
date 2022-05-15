package io.github.inggameteam.minigame.angangang.game.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.angangang.game.base.*
import io.github.inggameteam.minigame.angangang.game.base.Hub

class Hub(plugin: GamePlugin) : Hub(plugin), SpawnPlayer, SpawnOnJoin, VoidDeath, SpawnHealth,
        ParticleOnJoin
