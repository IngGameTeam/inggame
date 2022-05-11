package io.github.inggameteam.plugin.angangang

import io.github.inggameteam.minigame.GamePluginImpl
import io.github.inggameteam.minigame.angangang.game.impl.*
import io.github.inggameteam.minigame.angangang.handle.HandleDeath
import io.github.inggameteam.minigame.ui.DuelCommand
import io.github.inggameteam.minigame.ui.MinigameCommand
import io.github.inggameteam.minigame.angangang.game.impl.Hub
import io.github.inggameteam.party.PartyCacheSerializer
import io.github.inggameteam.plugin.angangang.handler.NoHunger
import io.github.inggameteam.plugin.angangang.handler.ReloadWatchDog

class Plugin : GamePluginImpl(
    hubName = "hub",
    worldName = "customized_minigame",
    width = 300, height = 128,
    init = arrayOf(
        ::Hub,
        ::TNTTag,
        ::TNTRun,
        ::RandomWeaponWar,
        ::HideAndSeek,
        ::UHC,
        ::Duel
    ),
) {

    override fun onEnable() {
        super.onEnable()
        MinigameCommand(this)
        ReloadWatchDog(this)
        NoHunger(this, worldName)
        HandleDeath(this)
        DuelCommand(this)
        PartyCacheSerializer.deserialize(this)
    }

    override fun onDisable() {
        super.onDisable()
        PartyCacheSerializer.serialize(this)
    }
}
