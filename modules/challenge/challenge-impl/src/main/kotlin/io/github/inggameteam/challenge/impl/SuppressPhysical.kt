package io.github.inggameteam.challenge.impl

import io.github.inggameteam.api.CountChallenge
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.event.GPlayerWinEvent
import io.github.inggameteam.minigame.event.GameBeginEvent
import io.github.inggameteam.minigame.event.GameJoinEvent
import io.github.inggameteam.minigame.impl.RandomWeaponWar
import io.github.inggameteam.mongodb.impl.ChallengeContainer
import io.github.inggameteam.player.hasTags
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageEvent

class SuppressPhysical(
    override val plugin: GamePlugin,
    override val item: ChallengeContainer,
) : CountChallenge<GamePlugin>, HandleListener(plugin) {
    override val name: String get() = "suppress-physical"
    override val goal get() = 2

    @Suppress("unused")
    @EventHandler
    fun onHit(event: EntityDamageEvent) {
        if (event.entityType !== EntityType.PLAYER) return
        val player = event.entity as Player
        reset(plugin[player])
    }

    @Suppress("unused")
    @EventHandler
    fun onHit(event: GameJoinEvent) {
        reset(plugin[event.player])
    }

    @Suppress("unused")
    @EventHandler
    fun onGameBegin(event: GameBeginEvent) {
        val game = event.game
        if (game is RandomWeaponWar) {
            game.joined.hasTags(PTag.PLAY).forEach { add(it) }
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onGameWin(event: GPlayerWinEvent) {
        val game = event.game
        if (game is RandomWeaponWar) {
            event.player.forEach { player -> add(player) }
        }
    }

}