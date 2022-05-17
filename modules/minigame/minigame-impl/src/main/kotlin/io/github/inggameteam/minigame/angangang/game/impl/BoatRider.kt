package io.github.inggameteam.minigame.angangang.game.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.base.NoDamage
import io.github.inggameteam.minigame.base.NoInteract
import io.github.inggameteam.minigame.base.Racing
import org.bukkit.entity.Boat
import org.bukkit.entity.Entity

class BoatRider(plugin: GamePlugin) : Racing(plugin), NoDamage, NoInteract {
    override val name = "boat-rider"
    override fun getRider(): Class<out Entity> = Boat::class.java
}
