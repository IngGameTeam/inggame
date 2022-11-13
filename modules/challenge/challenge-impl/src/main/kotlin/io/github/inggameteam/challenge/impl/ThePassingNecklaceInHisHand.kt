package io.github.inggameteam.challenge.impl

import io.github.inggameteam.api.CountChallenge
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.event.GPlayerWinEvent
import io.github.inggameteam.minigame.impl.BuildBattle
import io.github.inggameteam.mongodb.impl.ChallengeContainer
import org.bukkit.event.EventHandler

class ThePassingNecklaceInHisHand(override val plugin: GamePlugin,
                                  override val item: ChallengeContainer,
) : CountChallenge<GamePlugin>, HandleListener(plugin) {
    override val name: String get() = "the-passing-necklace-in-his-hand"
    override val goal get() = 1

    @Suppress
    @EventHandler
    fun onPlayerWinGame(event: GPlayerWinEvent) {
        if (event.game is BuildBattle) {
            event.player.forEach { add(it) }
        }
    }

}