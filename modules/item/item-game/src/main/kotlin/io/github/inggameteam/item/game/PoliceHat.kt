package io.github.inggameteam.item.game

import io.github.inggameteam.alert.AlertPlugin
import io.github.inggameteam.alert.Lang.lang
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.api.PluginHolder
import io.github.inggameteam.item.api.ItemComponentGetter
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.mongodb.impl.PurchaseContainer
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.scheduler.repeat
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryDragEvent

class PoliceHat(override val plugin: GamePlugin, val purchase: PurchaseContainer)
    : HandleListener(plugin), ItemComponentGetter {
    init {

        {
            val itemName = itemComp.string("police-hat", plugin.defaultLanguage)
            plugin.gameRegister.filter { it.name == plugin.gameRegister.hubName }.forEach { it ->
                it.joined.filter { purchase[it][itemName].amount.compareTo(1) == 0 }.forEach {
                    it.inventory.helmet = itemComp.item("${itemName}${getPlayerData(it)}", it.lang(plugin))
                }
            }

            true
        }.repeat(plugin, 1, 1)

    }

    @Suppress("unused")
    @EventHandler
    fun cancelInteractingHat(event: InventoryDragEvent) {
        val player = plugin[event.whoClicked as Player]
        val itemName = itemComp.string("police-hat", plugin.defaultLanguage)
        if (purchase[player][itemName].amount.compareTo(1) == 0) {
            val hub = plugin.gameRegister.first { it.name == plugin.gameRegister.hubName }
            if (hub.joined.contains(player)) {
                event.isCancelled = true
            }
        }
    }

    private fun getPlayerData(player: GPlayer): Int {
        val i = (player[POLICE_HAT] as? Int).let { if (it === null) 0 else it }
        player[POLICE_HAT] = (i + 1) % 17
        return i
    }

    companion object {
        const val POLICE_HAT = "policeHat"
    }

}