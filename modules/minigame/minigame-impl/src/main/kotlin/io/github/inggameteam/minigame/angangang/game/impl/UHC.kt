package io.github.inggameteam.minigame.angangang.game.impl

import io.github.inggameteam.base.*
import io.github.inggameteam.minigame.GamePlugin

@Deprecated("unused")
class UHC(plugin: GamePlugin) : SimpleGame, CompetitionImpl(plugin),
    DelayedPotion, OpenKitSelectMenuOnJoin, OpenKitSelectMenuOnClickItem, SpawnOnJoin, SpawnPlayer,
    TimerToEnd, TimerToTpAll, Broadcast
{
    override val name get() = "uhc"
}