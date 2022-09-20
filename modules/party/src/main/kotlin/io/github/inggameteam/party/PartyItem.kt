package io.github.inggameteam.party

import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.item.api.Drop
import io.github.inggameteam.item.api.Interact
import io.github.inggameteam.item.api.InventoryClick
import io.github.inggameteam.player.GPlayer

class PartyItem(override val plugin: PartyPlugin) : Interact, Drop, InventoryClick, HandleListener(plugin) {

    override val name get() = "party-menu"
    override fun use(name: String, player: GPlayer) {
        plugin.partyUI.partyMenu(player)
    }
}