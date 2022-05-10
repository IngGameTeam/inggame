package io.github.inggameteam.minigame.angangang.game.base

import io.github.inggameteam.minigame.base.LeaveWhenYouClickLeaveItem
import io.github.inggameteam.minigame.base.Sectional
import io.github.inggameteam.minigame.base.SpawnOnStart
import io.github.inggameteam.minigame.base.SpawnPlayer

interface SimpleGame : SpawnOnStart, SpawnPlayer, LeaveWhenYouClickLeaveItem, Sectional
