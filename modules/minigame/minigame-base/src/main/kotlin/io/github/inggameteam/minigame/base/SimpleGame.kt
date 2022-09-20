package io.github.inggameteam.minigame.base

interface SimpleGame : Sectional, VoidDeath, SpawnOnStart, SpawnOnJoin, SpawnPlayer,
    LeaveWhenYouClickLeaveItem, StartPlayersAmountAlert, RewardPoint, BeQuietOnWait,
        NoAnvilRename