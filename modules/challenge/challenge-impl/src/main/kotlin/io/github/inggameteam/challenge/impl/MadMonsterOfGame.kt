package io.github.inggameteam.challenge.impl

import io.github.inggameteam.api.CountChallenge
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.event.GPlayerWinEvent
import io.github.inggameteam.mongodb.impl.ChallengeContainer
import org.bukkit.event.EventHandler

class MadMonsterOfGame(override val plugin: GamePlugin,
                       override val item: ChallengeContainer,
)
    : CountChallenge<GamePlugin>, HandleListener(plugin) {
    override val name get() = "mad-monster-of-game"
    override val goal get() = 1500

    @Suppress("unused")
    @EventHandler
    fun onWin(event: GPlayerWinEvent) {
        event.game.joined.forEach {
            add(it)
        }
    }

}