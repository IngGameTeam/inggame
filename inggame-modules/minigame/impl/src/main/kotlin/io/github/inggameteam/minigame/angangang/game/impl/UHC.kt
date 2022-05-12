package io.github.inggameteam.minigame.angangang.game.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.angangang.game.base.OpenKitSelectMenuOnClickItem
import io.github.inggameteam.minigame.angangang.game.base.OpenKitSelectMenuOnJoin
import io.github.inggameteam.minigame.angangang.game.base.SimpleGame
import io.github.inggameteam.minigame.base.*

class UHC(plugin: GamePlugin) : SimpleGame, CompetitionImpl(plugin),
    DelayedPotion, OpenKitSelectMenuOnJoin, OpenKitSelectMenuOnClickItem, SpawnOnJoin, SpawnPlayer,
    TimerToEnd, TimerToTpAll
{
    override val name get() = "uhc"



}