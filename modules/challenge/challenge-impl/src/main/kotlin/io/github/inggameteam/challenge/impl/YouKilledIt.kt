package io.github.inggameteam.challenge.impl

import io.github.inggameteam.api.CountChallenge
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.event.GPlayerDeathEvent
import io.github.inggameteam.mongodb.impl.ChallengeContainer
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority

class YouKilledIt(override val plugin: GamePlugin,
                  override val item: ChallengeContainer,
)
    : CountChallenge<GamePlugin>, HandleListener(plugin) {
    override val name get() = "you-killed-it"
    override val goal get() = 1

    @Suppress("unused")
    @EventHandler(priority = EventPriority.LOW)
    fun onKill(event: GPlayerDeathEvent) {
        val killer = event.killer?: return
        val player = plugin[killer]
        if (plugin.gameRegister.getJoinedGame(player).name == plugin.gameRegister.hubName) {
            add(player)
        }
    }

}
