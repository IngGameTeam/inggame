package io.github.inggameteam.minigame.base

import io.github.inggameteam.alert.Lang.lang
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.player.GPlayer
import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory

interface SpawnPlayerRandomKit : SpawnPlayer {

    override fun spawn(player: GPlayer, spawn: String) {
        if (gameState === GameState.WAIT) {
            super.spawn(player, spawn)
            return
        }
        super.spawn(player, comp.stringListOrNull("spawn", player.lang(plugin))?.random()?: spawn)
    }

    override fun inventorySpawn(player: GPlayer, spawn: String): Inventory? {
        if (spawn == "random") {
            run {
                val inventory = Bukkit.createInventory(null, InventoryType.PLAYER)
                val lang = player.lang(plugin)
                comp.stringListOrNull("random-kit-slots", lang)?.forEach {
                    val i = it.toInt()
                    val randomItem = comp.stringListOrNull("random-kit-$it", lang)?.random() ?: return@run
                    inventory.setItem(i, comp.itemOrNull(randomItem, lang))
                }
                return inventory
            }
        }
        return super.inventorySpawn(player, spawn)
    }


}