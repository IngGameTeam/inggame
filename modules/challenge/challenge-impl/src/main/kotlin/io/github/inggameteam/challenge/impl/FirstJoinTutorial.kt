package io.github.inggameteam.challenge.impl

import io.github.inggameteam.api.CountChallenge
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.api.event.ChallengeArchiveEvent
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.event.GameJoinEvent
import io.github.inggameteam.minigame.impl.Tutorial
import io.github.inggameteam.mongodb.impl.ChallengeContainer
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority

class FirstJoinTutorial(override val plugin: GamePlugin,
                        override val item: ChallengeContainer,
)
    : CountChallenge<GamePlugin>, HandleListener(plugin) {
    override val name get() = "first-join-tutorial"
    override val goal get() = 1

    @Suppress("unused")
    @EventHandler
    fun onChat(event: GameJoinEvent) {
        if (event.join.name == plugin.gameRegister.hubName) {
            val player = event.player
            add(player)
            plugin.gameRegister.join(player, Tutorial.TUTORIAL_NAME)
        }
    }

    @Suppress("unused")
    @EventHandler(priority = EventPriority.LOW)
    fun onArchive(event: ChallengeArchiveEvent) {
        if (event.name == name) event.isCancelled = true
    }

}
