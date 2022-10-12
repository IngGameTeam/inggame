package io.github.inggameteam.plugin.angangang

import io.github.inggameteam.minigame.GamePluginImpl
import io.github.inggameteam.minigame.handle.*
import io.github.inggameteam.minigame.impl.Hub
import io.github.inggameteam.minigame.impl.Tutorial
import io.github.inggameteam.minigame.ui.MinigameCommand
import io.github.inggameteam.party.PartyCacheSerializer

@Suppress("unused")
class Plugin : GamePluginImpl(
    hubName = "hub",
    width = 300, height = 128,
    init = arrayOf(
        ::Hub,
        ::Tutorial,
    ),
) {

    override fun onEnable() {
        super.onEnable()
        PartyCacheSerializer.deserialize(this)
        MinigameCommand(this)
        ReloadWatchDog(this)
        NoHunger(this, worldName)
        HandleDeath(this)
        ClearEntityUnloadedChunk(this)
        HideJoinLeaveMessage(this)
        ArrowStuckPreventHandler(this)
        DisableCollision(this)
        AutoUpdater(this)
        JoinServerJoinGame(Tutorial.TUTORIAL_NAME, this)
    }

    override fun onDisable() {
        PartyCacheSerializer.serialize(this)
        super.onDisable()
    }
}
