package io.github.inggameteam.challenge.impl

import io.github.inggameteam.api.CountChallenge
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.item.impl.Meteor
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.mongodb.impl.ChallengeContainer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.ProjectileHitEvent

class TakeThisBoom(override val plugin: GamePlugin,
                   override val item: ChallengeContainer,
)
    : CountChallenge<GamePlugin>, HandleListener(plugin) {
    override val name get() = "take-this-boom"
    override val goal get() = 1

    @Suppress("unused")
    @EventHandler(priority = EventPriority.LOWEST)
    fun hit(event: ProjectileHitEvent) {
        if (event.entity.scoreboardTags.contains(Meteor.METEOR_TAG).not()) return
        val proj = event.entity
        val player = proj.shooter
        if (player !is Player) return
        add(plugin[player])
    }

}
