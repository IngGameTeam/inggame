package io.github.inggameteam.challenge.impl

import io.github.inggameteam.api.CountChallenge
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.event.GPlayerDeathEvent
import io.github.inggameteam.mongodb.impl.ChallengeContainer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerQuitEvent

class YouAreKiller(override val plugin: GamePlugin,
                   override val item: ChallengeContainer,
)
    : CountChallenge<GamePlugin>, HandleListener(plugin) {
    override val name get() = "you-are-killer"
    override val goal get() = 5

    @EventHandler(priority = EventPriority.LOW)
    fun leftServer(event: PlayerQuitEvent) {
        reset(plugin[event.player])
    }

    @EventHandler
    fun damage(event: EntityDamageEvent) {
        val player = event.entity
        if (player !is Player) return
        reset(plugin[player])
    }

    @EventHandler
    fun death(event: GPlayerDeathEvent) {
        val killer = event.killer
        if (killer === null) return
        val gKiller = plugin[killer]
        if (plugin.gameRegister.getJoinedGame(killer).name == plugin.gameRegister.hubName) return
        add(gKiller)
    }


}
