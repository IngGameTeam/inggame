package io.github.inggameteam.challenge.impl

import io.github.inggameteam.api.CountChallenge
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.event.GameJoinEvent
import io.github.inggameteam.minigame.impl.Tutorial
import io.github.inggameteam.mongodb.impl.ChallengeContainer
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.scheduler.delay
import org.bukkit.event.EventHandler

class FirstJoinTutorial(override val plugin: GamePlugin,
                        override val item: ChallengeContainer,
)
    : CountChallenge<GamePlugin>, HandleListener(plugin) {
    override val name get() = "first-join-tutorial"
    override val goal get() = 1

    @Suppress("unused")
    @EventHandler
    fun onJoin(event: GameJoinEvent) {
        if (event.join.name == plugin.gameRegister.hubName) {
            val player = event.player
            add(player)
        }
    }

    override fun goal(player: GPlayer) {
        super.goal(player)
        ;{
            plugin.gameRegister.join(player, Tutorial.TUTORIAL_NAME)
        }.delay(plugin, 10).apply { player.addTask(this) }
    }

}