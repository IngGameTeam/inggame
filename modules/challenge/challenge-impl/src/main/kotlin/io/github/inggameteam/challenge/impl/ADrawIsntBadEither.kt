package io.github.inggameteam.challenge.impl

import io.github.inggameteam.api.CountChallenge
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.event.GPlayerWinEvent
import io.github.inggameteam.minigame.event.GameJoinEvent
import io.github.inggameteam.mongodb.impl.ChallengeContainer
import io.github.inggameteam.player.hasTags
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockBreakEvent

class ADrawIsntBadEither(override val plugin: GamePlugin,
                         override val item: ChallengeContainer,
)
    : CountChallenge<GamePlugin>, HandleListener(plugin) {
    override val name get() = "a-draw-isnt-bad-either"
    override val goal get() = 1

    @Suppress("unused")
    @EventHandler
    fun onWin(event: GPlayerWinEvent) {
        if (event.player.isEmpty()) {
            event.game.joined.forEach {
                if (challenge(it).data == 0) {
                    goal(it)
                }
            }
        }
    }

}
