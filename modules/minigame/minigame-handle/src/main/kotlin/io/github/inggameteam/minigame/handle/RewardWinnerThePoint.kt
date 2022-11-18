package io.github.inggameteam.minigame.handle

import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.base.RewardPoint
import io.github.inggameteam.minigame.event.GPlayerWinEvent
import io.github.inggameteam.minigame.handle.event.RewardPointEvent
import io.github.inggameteam.mongodb.impl.UserContainer
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler

class RewardWinnerThePoint(val plugin: GamePlugin, private val user: UserContainer) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onWin(event: GPlayerWinEvent) {
        val game = event.game
        if (game is RewardPoint) {
            event.player.forEach {
                val pointAdder = game.rewardPoint(it)
                if (pointAdder > 0) {
                    Bukkit.getPluginManager().callEvent(RewardPointEvent(it, game, pointAdder))
                    user[it].point += pointAdder
                    game.comp.send("POINT_REWARD", it, pointAdder)
                }
            }
        }
    }

}