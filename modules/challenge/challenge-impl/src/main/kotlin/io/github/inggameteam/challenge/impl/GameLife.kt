package io.github.inggameteam.challenge.impl

import io.github.inggameteam.api.CountChallenge
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.event.GPlayerWinEvent
import io.github.inggameteam.mongodb.impl.ChallengeContainer
import io.github.inggameteam.player.hasTags
import org.bukkit.event.EventHandler

class GameLife(
    override val plugin: GamePlugin,
    override val item: ChallengeContainer,
    )
    : CountChallenge<GamePlugin>, HandleListener(plugin) {
    override val name get() = "game-life"
    override val goal get() = 400

    @Suppress("unused")
    @EventHandler
    fun partyCreate(event: GPlayerWinEvent) {
        event.game.joined.hasTags(PTag.PLAY).forEach { add(it) }
    }

}