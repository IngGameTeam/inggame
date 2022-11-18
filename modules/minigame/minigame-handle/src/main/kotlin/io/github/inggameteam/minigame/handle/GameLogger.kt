package io.github.inggameteam.minigame.handle

import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.minigame.base.RewardPoint
import io.github.inggameteam.minigame.event.*
import io.github.inggameteam.minigame.handle.event.RewardPointEvent
import io.github.inggameteam.mongodb.impl.UserLog
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.Plugin

class GameLogger(plugin: Plugin, val userLog: UserLog) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        userLog.insert(event.player, "server_join") {}
    }

    @Suppress("unused")
    @EventHandler
    fun onJoin(event: PlayerQuitEvent) {
        userLog.insert(event.player, "server_left") {}
    }

    @Suppress("unused")
    @EventHandler
    fun onJoinGame(event: GameJoinEvent) {
        val join = event.join
        userLog.insert(event.player, "join_game") {
            "join_type" to event.joinType.name
            "game_state" to join.gameState.name
            "game_name" to join.name
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onLeftGame(event: GameLeftEvent) {
        val join = event.left
        userLog.insert(event.player, "left_game") {
            "left_type" to event.leftType.name
            "game_state" to join.gameState.name
            "game_name" to join.name
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onGameStart(event: GameBeginEvent) {
        val game = event.game
        game.joined.forEach { player ->
            userLog.insert(player, "game_begin") {
                "game_name" to game.name
            }
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onGameWin(event: GPlayerWinEvent) {
        val game = event.game
        event.player.forEach { player ->
            userLog.insert(player, "game_win") {
                "game_name" to game.name
            }
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onPointReward(event: RewardPointEvent) {
        val game = event.game
        userLog.insert(event.player, "point_reward") {
            "amount" to event.amount
            "game" to game
            "game_state" to game.gameState.name
        }
    }



}