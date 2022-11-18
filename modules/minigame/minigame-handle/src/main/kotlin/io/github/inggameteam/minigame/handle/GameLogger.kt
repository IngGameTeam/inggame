package io.github.inggameteam.minigame.handle

import io.github.inggameteam.api.HandleListener
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
            put("join_type", event.joinType.name)
            put("game_state", join.gameState.name)
            put("game_name", join.name)
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onLeftGame(event: GameLeftEvent) {
        val join = event.left
        userLog.insert(event.player, "left_game") {
            put("left_type", event.leftType.name)
            put("game_state", join.gameState.name)
            put("game_name", join.name)
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onGameStart(event: GameBeginEvent) {
        val game = event.game
        game.joined.forEach { player ->
            userLog.insert(player, "game_begin") {
                put("game_name", game.name)
            }
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onGameWin(event: GPlayerWinEvent) {
        val game = event.game
        event.player.forEach { player ->
            userLog.insert(player, "game_win") {
                put("game_name", game.name)
            }
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onPointReward(event: RewardPointEvent) {
        val game = event.game
        userLog.insert(event.player, "point_reward") {
            put("amount", event.amount)
            put("game", game)
        }
    }

}