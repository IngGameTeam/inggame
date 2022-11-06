package io.github.inggameteam.item.game

import io.github.inggameteam.alert.Lang.lang
import io.github.inggameteam.item.api.Interact
import io.github.inggameteam.item.api.Item
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.event.GameJoinEvent
import io.github.inggameteam.player.GPlayer
import org.bukkit.event.EventHandler

class ShuffleGame(override val plugin: GamePlugin) : Item, Interact {
    override val name: String = "shuffle-game"
    override fun use(name: String, player: GPlayer) {
        shuffleJoin(player)
    }

    @Suppress("unused")
    @EventHandler
    fun onPlayerJoinHub(event: GameJoinEvent) {
        if (plugin.gameRegister.hubName != event.join.name) return
        if (event.player[SHUFFLE_KEY] !== null) {

        }
    }

    fun shuffleJoin(player: GPlayer) {
        plugin.gameRegister.join(player, randomGame(player), forceCreateGame = true)
    }

    fun randomGame(player: GPlayer): String {
        val games = itemComp.stringList("$name-games", player.lang(plugin))
        return games.random()
    }

    companion object {
        const val SHUFFLE_KEY = "HandleShuffle"
    }
}