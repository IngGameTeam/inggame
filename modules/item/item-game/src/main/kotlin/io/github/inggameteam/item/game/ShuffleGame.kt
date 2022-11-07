package io.github.inggameteam.item.game

import io.github.inggameteam.alert.Lang.lang
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.item.api.Interact
import io.github.inggameteam.item.api.Item
import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.event.GameJoinEvent
import io.github.inggameteam.minigame.event.GameLeftEvent
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.scheduler.runNow
import org.bukkit.event.EventHandler

class ShuffleGame(override val plugin: GamePlugin) : Item, Interact, HandleListener(plugin) {
    override val name: String = "shuffle-game"
    override fun use(name: String, player: GPlayer) {
        shuffleJoin(player)
    }



    @Suppress("unused")
    @EventHandler
    fun onPlayerJoinHub(event: GameLeftEvent) {
        if (plugin.gameRegister.hubName != event.left.name) {
            val game = event.player[SHUFFLE_KEY] as? Game
            if (game !== null && game.gameState === GameState.STOP) {
                event.player.remove(SHUFFLE_KEY)
                ;{ shuffleJoin(event.player) }.runNow(plugin)
            }
        }
    }

    private fun shuffleJoin(player: GPlayer) {
        plugin.gameRegister.join(player, randomGame(player), forceCreateGame = true)
        player[SHUFFLE_KEY] = plugin.gameRegister.getJoinedGame(player)
    }

    private fun randomGame(player: GPlayer): String {
        val games = itemComp.stringList("$name-games", player.lang(plugin))
        return games.random()
    }

    companion object {
        const val SHUFFLE_KEY = "HandleShuffle"
    }
}