package io.github.inggameteam.challenge.impl

import io.github.inggameteam.api.CountChallenge
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.event.GameJoinEvent
import io.github.inggameteam.mongodb.impl.ChallengeContainer
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockBreakEvent

class AmazingHardWork(override val plugin: GamePlugin,
                      override val item: ChallengeContainer,
)
    : CountChallenge<GamePlugin>, HandleListener(plugin) {
    override val name get() = "amazing-hard-work"
    override val goal = 32 * 32 * 3

    @Suppress("unused")
    @EventHandler
    fun onJoinGame(event: GameJoinEvent) {
        reset(event.player)
    }

    @Suppress("unused")
    @EventHandler
    fun onBreakBlock(event: BlockBreakEvent) {
        val player = plugin[event.player]
        if (plugin.gameRegister.getJoinedGame(player).name == "spleef") {
            add(player)
        }
    }



}
