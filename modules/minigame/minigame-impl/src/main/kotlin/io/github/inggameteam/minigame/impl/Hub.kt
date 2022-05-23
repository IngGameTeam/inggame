package io.github.inggameteam.minigame.impl

import io.github.inggameteam.minigame.base.*
import io.github.inggameteam.minigame.base.Hub
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.event.GPlayerSpawnEvent
import io.github.inggameteam.player.GPlayer
import org.bukkit.Location
import org.bukkit.event.EventHandler

class Hub(plugin: GamePlugin) : Hub(plugin), SpawnPlayer, SpawnOnJoin, VoidDeath, SpawnHealth,
    ParticleOnSpawn, ClearPotionOnJoin, PreventFallDamage {
    override fun tpSpawn(player: GPlayer, spawn: String): Location? {
        return if (isInSector(player.location)) null else super.tpSpawn(player, spawn)
    }

    override fun onJoinParticle(event: GPlayerSpawnEvent) {
        if (isInSector(event.player.location)) super.onJoinParticle(event)
    }
}