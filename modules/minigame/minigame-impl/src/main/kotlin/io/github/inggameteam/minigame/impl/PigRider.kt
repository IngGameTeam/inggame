package io.github.inggameteam.minigame.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.base.*
import org.bukkit.entity.Pig

class PigRider(plugin: GamePlugin) : Racing(plugin), SimpleGame, NoDamage, NoInteract, GoalIn {
    override val name get() = "pig-rider"
    override fun getRider() = Pig::class.java
}