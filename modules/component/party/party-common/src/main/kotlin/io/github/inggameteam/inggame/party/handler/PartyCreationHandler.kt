package io.github.inggameteam.inggame.party.handler

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.party.event.CreatePartyEvent
import io.github.inggameteam.inggame.party.event.JoinPartyEvent
import io.github.inggameteam.inggame.party.wrapper.Party
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import kotlin.system.measureTimeMillis

class PartyCreationHandler(plugin: IngGamePlugin) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onJoinPartyThenCreateParty(event: JoinPartyEvent) {
        println(measureTimeMillis{ if (isNotHandler(event.joined)) return })
        if (event.joined.joinedPlayers.size == 1) {
            Bukkit.getPluginManager().callEvent(CreatePartyEvent(event.player))
        }
    }


}