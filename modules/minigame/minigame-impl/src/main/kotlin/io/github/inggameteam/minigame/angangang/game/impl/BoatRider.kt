package io.github.inggameteam.minigame.angangang.game.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.base.NoDamage
import io.github.inggameteam.minigame.base.NoInteract
import io.github.inggameteam.minigame.base.Racing
import io.github.inggameteam.minigame.base.SimpleGame
import org.bukkit.entity.Boat

class BoatRider(plugin: GamePlugin) : Racing(plugin), NoDamage, NoInteract, SimpleGame {
    override val name get() = "boat-rider"
    override fun getRider() = Boat::class.java
}
