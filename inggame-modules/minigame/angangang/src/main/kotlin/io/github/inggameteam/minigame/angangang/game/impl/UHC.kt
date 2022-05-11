package io.github.inggameteam.minigame.angangang.game.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.angangang.game.base.OpenKitSelectMenuOnClickItem
import io.github.inggameteam.minigame.angangang.game.base.OpenKitSelectMenuOnJoin
import io.github.inggameteam.minigame.angangang.game.base.SimpleGame
import io.github.inggameteam.minigame.base.CompetitionImpl
import io.github.inggameteam.minigame.base.DelayedPotion
import io.github.inggameteam.minigame.base.SpawnOnJoin
import io.github.inggameteam.minigame.base.SpawnPlayer

class UHC(plugin: GamePlugin) : SimpleGame, CompetitionImpl(plugin),
    DelayedPotion, OpenKitSelectMenuOnJoin, OpenKitSelectMenuOnClickItem, SpawnOnJoin, SpawnPlayer {
    override val name get() = "uhc"



}