package io.github.inggameteam.plugin.angangang.game

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.Sector
import io.github.inggameteam.minigame.base.Hub
import io.github.inggameteam.minigame.base.SpawnOnJoin
import io.github.inggameteam.minigame.base.SpawnPlayer
import io.github.inggameteam.minigame.event.GPlayerSpawnEvent

class Hub(plugin: GamePlugin, point: Sector) : Hub(plugin, point), SpawnPlayer, SpawnOnJoin
