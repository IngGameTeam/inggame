package io.github.inggameteam.minigame.angangang.game.base

interface SimpleGame : Sectional, VoidDeath, SpawnOnStart, SpawnOnJoin, SpawnPlayer,
    LeaveWhenYouClickLeaveItem, StartPlayersAmountAlert
