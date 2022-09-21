package io.github.inggameteam.minigame.handle

import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.JoinType
import io.github.inggameteam.minigame.LeftType
import io.github.inggameteam.party.event.JoinPartyEvent
import org.bukkit.event.EventHandler

class SpectateOnJoinParty(val plugin: GamePlugin) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onJoin(event: JoinPartyEvent) {
        val joinedGame = plugin.gameRegister.getJoinedGame(event.joined.leader)
        if (joinedGame.name != plugin.gameRegister.hubName) {
            plugin.gameRegister.getJoinedGame(event.player).leftGame(event.player, LeftType.DUE_TO_MOVE_ANOTHER_GAME)
            joinedGame.joinGame(event.player, JoinType.SPECTATE)
        }
    }

}