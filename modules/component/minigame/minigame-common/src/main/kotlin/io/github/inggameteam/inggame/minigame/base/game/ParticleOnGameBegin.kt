package io.github.inggameteam.inggame.minigame.base.game

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.minigame.base.game.event.GameBeginEvent
import io.github.inggameteam.inggame.minigame.base.player.PTag
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.hasTags
import org.bukkit.Particle
import org.bukkit.event.EventHandler

class ParticleOnGameBegin(plugin: IngGamePlugin) : HandleListener(plugin) {

    private fun afterParticle(game: Game) {
        game.joinedPlayers.hasTags(PTag.PLAY).forEach {
            it.world.spawnParticle(Particle.END_ROD, it.eyeLocation.clone(), 20)
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onBeginGameDoParticle(event: GameBeginEvent) {
        val game = event.game
        if (isNotHandler(game)) return
        afterParticle(game)
    }

}