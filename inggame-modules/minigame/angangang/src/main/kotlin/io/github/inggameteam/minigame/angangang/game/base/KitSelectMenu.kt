package io.github.inggameteam.minigame.angangang.game.base

import io.github.inggameteam.alert.component.Lang.lang
import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.base.SpawnPlayer
import io.github.inggameteam.player.GPlayer
import io.github.monun.invfx.InvFX
import io.github.monun.invfx.openFrame
import net.kyori.adventure.text.Component
import org.bukkit.inventory.Inventory

interface KitSelectMenu : Game, SpawnPlayer {

    override fun inventorySpawn(player: GPlayer, spawn: String): Inventory? {
        return if (gameState === GameState.WAIT) super.inventorySpawn(player, spawn)
        else super.inventorySpawn(player, playerData[player]!![SELECTED_KIT]!!.toString())
    }

    fun open(player: GPlayer) {
        val lang = player.lang(plugin)
        val inventory = comp.inventory("kit", lang)
        val items = comp.stringList("kit", lang).map { Pair(it, comp.item(it, lang)) }
        InvFX.frame(3, Component.text(comp.string("kit-inventory-title", lang))) {
            for (x in 0 until 9)
            {
                for (y in 0 until 3)
                {
                    slot(x, y) {
                        item = inventory.getItem(x + y * 9)
                        onClick { event ->
                            items.firstOrNull { it.second.isSimilar(event.currentItem) }?.apply {
                                playerData[player]!![SELECTED_KIT] = first
                                comp.send("selected", player, first)
                                player.closeInventory()
                            }
                        }
                    }
                }
            }
        }.apply { player.openFrame(this) }
    }

    companion object {
        const val SELECTED_KIT = "selectedKit"
    }

}