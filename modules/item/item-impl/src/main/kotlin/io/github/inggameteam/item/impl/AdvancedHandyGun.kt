package io.github.inggameteam.item.impl

import io.github.inggameteam.alert.AlertPlugin
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.item.api.Interact
import io.github.inggameteam.item.api.Item
import io.github.inggameteam.player.GPlayer
import org.bukkit.Location
import org.bukkit.Particle
import kotlin.math.abs

class AdvancedHandyGun(override val plugin: AlertPlugin) : Item, Interact, HandleListener(plugin) {

    override val name get() = "advanced-handy-gun"

    override fun use(name: String, player: GPlayer) {
        linear(Particle.ASH, player.eyeLocation, 10.0, 0.5)
    }

    private fun linear(particle: Particle, direction: Location, distance: Double, step: Double) {
        var adder = .0
        val world = direction.world!!
        while (abs(adder) < abs(distance)) {
            val location = direction.toVector().clone().multiply(adder).toLocation(world)
            world.spawnParticle(particle, location, 1)
            adder += step
        }
    }


}