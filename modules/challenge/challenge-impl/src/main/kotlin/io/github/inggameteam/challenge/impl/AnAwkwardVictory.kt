package io.github.inggameteam.challenge.impl

import io.github.inggameteam.api.CountChallenge
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.event.GPlayerDeathEvent
import io.github.inggameteam.minigame.event.GPlayerWinEvent
import io.github.inggameteam.minigame.event.GameJoinEvent
import io.github.inggameteam.mongodb.impl.ChallengeContainer
import io.github.inggameteam.player.hasTags
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockBreakEvent

class AnAwkwardVictory(override val plugin: GamePlugin,
                       override val item: ChallengeContainer,
)
    : CountChallenge<GamePlugin>, HandleListener(plugin) {
    override val name get() = "an-awkward-victory"
    override val goal get() = 10

    @Suppress("unused")
    @EventHandler
    fun onJoinGame(event: GameJoinEvent) {
        reset(event.player)
    }

    @Suppress("unused")
    @EventHandler
    fun onKill(event: GPlayerDeathEvent) {
        val killer = event.killer?: return
        val player = plugin[killer]
        if (plugin.gameRegister.getJoinedGame(player).name == "spleef") {
            add(player)
        }
    }

}
