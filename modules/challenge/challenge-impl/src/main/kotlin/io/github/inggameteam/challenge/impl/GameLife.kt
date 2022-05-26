package io.github.inggameteam.challenge.impl

import io.github.inggameteam.api.Holder
import io.github.inggameteam.api.PluginHolder
import io.github.inggameteam.api.CountChallenge
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.mongodb.impl.ChallengeContainer
import io.github.inggameteam.party.event.CreatePartyEvent
import org.bukkit.event.EventHandler

class GameLife(
    override val plugin: GamePlugin,
    override val item: ChallengeContainer,
    )
    : CountChallenge<GamePlugin>, HandleListener(plugin) {
    override val name get() = "game-life"
    override val goal get() = 3

    @Suppress("unused")
    @EventHandler
    fun partyCreate(event: CreatePartyEvent) {
        add(event.player)
    }

}