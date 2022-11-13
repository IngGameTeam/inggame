package io.github.inggameteam.challenge.impl

import io.github.inggameteam.api.CountChallenge
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.mongodb.impl.ChallengeContainer
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.potion.PotionEffectType


class Ttukbaegi(
    override val plugin: GamePlugin,
    override val item: ChallengeContainer,
) : CountChallenge<GamePlugin>, HandleListener(plugin) {
    override val name: String get() = "ttukbaegi"
    override val goal get() = 1200


    @Suppress("unused")
    @EventHandler
    fun onDamage(event: EntityDamageEvent) {
        if (event.entityType !== EntityType.PLAYER) return
        val player = event.entity as Player
        if (isCritical(player)) {
            add(plugin[player])
        }

    }

    private fun isCritical(player: Player): Boolean {
        return player.fallDistance > 0.0f &&
                !player.isOnGround &&
                !player.isInsideVehicle &&
                !player.hasPotionEffect(PotionEffectType.BLINDNESS) && player.location.block
            .type != Material.LADDER && player.location.block.type !== Material.VINE
    }


}