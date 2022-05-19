package io.github.inggameteam.minigame.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.base.*
import org.bukkit.entity.Boat

class BoatRider(plugin: GamePlugin) : Racing(plugin), NoDamage, NoInteract, SimpleGame, GoalIn {
    override val name get() = "boat-rider"
    override fun getRider() = Boat::class.java
}
