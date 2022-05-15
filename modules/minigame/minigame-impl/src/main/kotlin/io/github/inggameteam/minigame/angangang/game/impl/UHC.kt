package io.github.inggameteam.minigame.angangang.game.impl

import io.github.inggameteam.minigame.base.*
import io.github.inggameteam.minigame.GamePlugin

@Deprecated("unused")
class UHC(plugin: GamePlugin) : SimpleGame, CompetitionImpl(plugin),
    DelayedPotion, OpenKitSelectMenuOnJoin, OpenKitSelectMenuOnClickItem, SpawnOnJoin, SpawnPlayer,
    TimerToEnd, TimerToTpAll, io.github.inggameteam.minigame.base.Broadcast
{
    override val name get() = "uhc"
}