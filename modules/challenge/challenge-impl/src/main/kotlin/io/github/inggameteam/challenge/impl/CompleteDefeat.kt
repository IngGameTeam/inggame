package io.github.inggameteam.challenge.impl

import io.github.inggameteam.api.CountChallenge
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.event.GPlayerWinEvent
import io.github.inggameteam.minigame.impl.Soccer
import io.github.inggameteam.mongodb.impl.ChallengeContainer
import io.github.inggameteam.player.hasTags
import org.bukkit.event.EventHandler

class CompleteDefeat(override val plugin: GamePlugin,
                     override val item: ChallengeContainer,
) : CountChallenge<GamePlugin>, HandleListener(plugin) {
    override val name: String get() = "complete-defeat"
    override val goal get() = 1

    @Suppress("unused")
    @EventHandler
    fun onPlayerWinGame(event: GPlayerWinEvent) {
        val game = event.game
        if (game is Soccer) {
            if (game.blueScore == 0 && game.redScore == Soccer.GOAL_SCORE) {
                game.joined.hasTags(PTag.BLUE).forEach {
                    add(it)
                }
            } else if (game.redScore == 0 && game.blueScore == Soccer.GOAL_SCORE) {
                game.joined.hasTags(PTag.RED).forEach {
                    add(it)
                }

            }
        }
    }

}