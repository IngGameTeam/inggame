package io.github.inggameteam.minigame.angangang.game.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.Sector
import io.github.inggameteam.minigame.base.*
import io.github.inggameteam.minigame.base.Hub

class Hub(plugin: GamePlugin, point: Sector) : Hub(plugin, point), SpawnPlayer, SpawnOnJoin, VoidDeath, SpawnHealth