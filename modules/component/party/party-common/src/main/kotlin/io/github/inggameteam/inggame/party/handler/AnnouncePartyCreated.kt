package io.github.inggameteam.inggame.party.handler

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.party.event.CreatePartyEvent
import io.github.inggameteam.inggame.party.wrapper.PartyAlertImp
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.event.EventHandler
import kotlin.system.measureTimeMillis

class AnnouncePartyCreated(plugin: IngGamePlugin) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onPartyCreated(event: CreatePartyEvent) {
        val player = event.player
        println(measureTimeMillis {
            if (isNotHandler(player)) return
        })
        player[::PartyAlertImp].PARTY_CREATED.send(player)
    }

}