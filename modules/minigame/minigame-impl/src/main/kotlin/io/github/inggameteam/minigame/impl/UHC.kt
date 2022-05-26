package io.github.inggameteam.minigame.impl

import io.github.inggameteam.minigame.base.*
import io.github.inggameteam.minigame.GamePlugin

@Deprecated("unused")
class UHC(plugin: GamePlugin) : SimpleGame, CompetitionImpl(plugin),
    DelayedPotion, OpenKitSelectMenuOnJoin, OpenKitSelectMenuOnClickItem, SpawnOnJoin, SpawnPlayer,
    TimerToEnd, TimerToTpAll, Broadcast
{
    override val name get() = "uhc"
}