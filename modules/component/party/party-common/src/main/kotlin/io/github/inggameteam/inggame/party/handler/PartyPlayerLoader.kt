package io.github.inggameteam.inggame.party.handler

import io.github.inggameteam.inggame.party.component.PartyInstanceService
import io.github.inggameteam.inggame.party.component.PartyPlayerService
import io.github.inggameteam.inggame.party.wrapper.PartyPlayerImp
import io.github.inggameteam.inggame.party.wrapper.PartyServer
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.Listener
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent

class PartyPlayerLoader(
    private val partyInstanceService: PartyInstanceService,
    private val partyPlayerService: PartyPlayerService,
    private val partyServer: PartyServer,
    plugin: IngGamePlugin
) : Listener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        partyInstanceService.join(partyServer.defaultParty, partyPlayerService[event.player.uniqueId, ::PartyPlayerImp])
    }

    @Suppress("unused")
    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        partyInstanceService.left(partyPlayerService[event.player.uniqueId, ::PartyPlayerImp])
    }

    @Suppress("unused")
    @EventHandler
    fun onKick(event: PlayerKickEvent) {
        partyInstanceService.left(partyPlayerService[event.player.uniqueId, ::PartyPlayerImp])
    }

}