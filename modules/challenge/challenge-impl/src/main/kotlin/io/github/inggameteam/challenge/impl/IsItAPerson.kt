package io.github.inggameteam.challenge.impl

import io.github.inggameteam.api.CountChallenge
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.event.GPlayerWinEvent
import io.github.inggameteam.mongodb.impl.ChallengeContainer
import org.bukkit.event.EventHandler

class IsItAPerson(override val plugin: GamePlugin,
                  override val item: ChallengeContainer,
)
    : CountChallenge<GamePlugin>, HandleListener(plugin) {
    override val name get() = "is-it-a-person"
    override val goal get() = 10

    @Suppress("unused")
    @EventHandler
    fun onWin(event: GPlayerWinEvent) {
        val player = event.player
        player.forEach {
            add(it)
        }
        ArrayList(event.game.joined).apply { removeAll(player) }.forEach {
            reset(it)
        }
    }

}