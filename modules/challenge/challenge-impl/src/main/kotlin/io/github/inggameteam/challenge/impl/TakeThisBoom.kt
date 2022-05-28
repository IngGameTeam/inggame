package io.github.inggameteam.challenge.impl

import io.github.inggameteam.api.CountChallenge
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.item.impl.Meteor
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.event.GPlayerDeathEvent
import io.github.inggameteam.minigame.event.GPlayerWinEvent
import io.github.inggameteam.minigame.event.GameJoinEvent
import io.github.inggameteam.mongodb.impl.ChallengeContainer
import io.github.inggameteam.player.hasTags
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent

class TakeThisBoom(override val plugin: GamePlugin,
                   override val item: ChallengeContainer,
)
    : CountChallenge<GamePlugin>, HandleListener(plugin) {
    override val name get() = "take-this-boom"
    override val goal get() = 1

    @Suppress("unused")
    @EventHandler
    fun hit(event: EntityDamageByEntityEvent) {
        if (event.entity.scoreboardTags.contains(Meteor.METEOR_TAG).not()) return
        val proj = event.entity
        if (proj !is Projectile) return
        val player = proj.shooter
        if (player !is Player) return
        if (event.finalDamage < player.health) return
        add(plugin[player])
    }

}
