package io.github.inggameteam.item.game

import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.item.api.Interact
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.mongodb.impl.PurchaseContainer
import io.github.inggameteam.player.GPlayer

class TutorialBook(override val plugin: GamePlugin, private val purchase: PurchaseContainer) : Interact, HandleListener(plugin)   {
    override val name get() = "tutorial-book"
    override fun use(name: String, player: GPlayer) {
        purchase[player][name].apply {
            if (amount > 0) amount -= 1
        }
        plugin.gameRegister.join(player, "tutorial")
    }
}