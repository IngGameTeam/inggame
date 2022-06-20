package io.github.inggameteam.minigame.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.base.*
import io.github.inggameteam.player.hasTags
import org.bukkit.entity.Pig
import org.bukkit.event.EventHandler

class PigRider(plugin: GamePlugin) : Racing(plugin), SimpleGame, NoDamage, NoInteract, GoalIn {
    override val name get() = "pig-rider"
    override fun getRider() = Pig::class.java

    override fun beginGame() {
        super.beginGame()
        joined.hasTags(PTag.PLAY).forEach { it.inventory.heldItemSlot = 0 }
    }
}