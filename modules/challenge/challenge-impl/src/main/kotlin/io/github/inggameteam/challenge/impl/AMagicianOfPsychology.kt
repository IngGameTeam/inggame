package io.github.inggameteam.challenge.impl

import io.github.inggameteam.api.CountChallenge
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.event.GPlayerDeathEvent
import io.github.inggameteam.minigame.event.GPlayerWinEvent
import io.github.inggameteam.minigame.event.GameJoinEvent
import io.github.inggameteam.mongodb.impl.ChallengeContainer
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockBreakEvent

class AMagicianOfPsychology(override val plugin: GamePlugin,
                            override val item: ChallengeContainer,
)
    : CountChallenge<GamePlugin>, HandleListener(plugin) {
    override val name get() = "a-magician-of-psychology"
    override val goal get() = 1

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

    @Suppress("unused")
    @EventHandler
    fun onKill(event: GPlayerDeathEvent) {
        val killer = event.killer?: return
        val player = plugin[killer]
        if (plugin.gameRegister.getJoinedGame(player).name == "spleef") {
            reset(player)
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onWin(event: GPlayerWinEvent) {
        if (event.game.name == "spleef") {
            event.player.forEach {
                if (challenge(it).data == 0) {
                    goal(it)
                }
            }
        }
    }

}