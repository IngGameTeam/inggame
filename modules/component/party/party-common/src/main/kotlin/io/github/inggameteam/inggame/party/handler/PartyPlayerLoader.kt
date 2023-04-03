package io.github.inggameteam.inggame.party.handler

import io.github.inggameteam.inggame.party.component.PartyInstanceService
import io.github.inggameteam.inggame.party.component.PartyPlayerService
import io.github.inggameteam.inggame.party.wrapper.PartyPlayer
import io.github.inggameteam.inggame.party.wrapper.PartyPlayerImp
import io.github.inggameteam.inggame.party.wrapper.PartyServer
import io.github.inggameteam.inggame.player.PlayerInstanceService
import io.github.inggameteam.inggame.player.handler.PlayerLoader
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.Listener
import io.github.inggameteam.inggame.utils.event.IngGamePluginEnableEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.UUID

class PartyPlayerLoader(
    private val partyInstanceService: PartyInstanceService,
    private val partyPlayerService: PartyPlayerService,
    private val partyServer: PartyServer,
    @Suppress("unused")
    private val playerLoader: PlayerLoader,
    private val playerInstanceService: PlayerInstanceService,
    plugin: IngGamePlugin
) : Listener(plugin) {

    @Suppress("unused")
    @EventHandler(priority = EventPriority.LOW)
    fun onEnable(event: IngGamePluginEnableEvent) {
        playerInstanceService.getAll().map { it.name as UUID }.forEach { join(it) }
    }

    @Suppress("unused")
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        join(event.player.uniqueId)
    }

    @Suppress("unused")
    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        quit(event.player.uniqueId)
    }

    @Suppress("unused")
    @EventHandler
    fun onKick(event: PlayerKickEvent) {
        quit(event.player.uniqueId)
    }

    private fun join(uniqueId: UUID) {
        join(partyPlayerService[uniqueId, ::PartyPlayerImp])
    }

    private fun join(partyPlayer: PartyPlayer) {
        partyInstanceService.join(partyServer.defaultParty, partyPlayer)
    }

    private fun quit(uniqueId: UUID) {
        partyInstanceService.left(partyPlayerService[uniqueId, ::PartyPlayerImp])
    }

}