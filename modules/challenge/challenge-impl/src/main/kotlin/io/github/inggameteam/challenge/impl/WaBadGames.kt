package io.github.inggameteam.challenge.impl

import io.github.inggameteam.api.CountChallenge
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.mongodb.impl.ChallengeContainer
import io.github.inggameteam.scheduler.runNow
import org.bukkit.event.EventHandler
import org.bukkit.event.player.AsyncPlayerChatEvent

class WaBadGames(override val plugin: GamePlugin,
                 override val item: ChallengeContainer,
)
    : CountChallenge<GamePlugin>, HandleListener(plugin) {
    override val name get() = "wa-bad-games"
    override val goal get() = 1

    @Suppress("unused")
    @EventHandler
    fun onChat(event: AsyncPlayerChatEvent) {
        if (event.message == comp.string("$name-value", plugin.defaultLanguage)) {
            val player = plugin[event.player]
            { if (player.isOnline) add(player) }.runNow(plugin)
        }
    }

}
