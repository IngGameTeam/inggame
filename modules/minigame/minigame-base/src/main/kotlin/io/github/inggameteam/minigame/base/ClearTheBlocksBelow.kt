package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.event.GameBeginEvent
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.hasTags
import io.github.inggameteam.scheduler.delay
import io.github.inggameteam.scheduler.repeat
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.event.EventHandler

interface ClearTheBlocksBelow : VoidDeath {

    val clearBlockBelowDelay get() = 7L
    val primaryCoolDelay get() = comp.intOrNull("primary-cool-delay")?.toLong()?: 15L

    @Suppress("unused")
    @EventHandler
    fun beginGameClearTheBlocksBelow(event: GameBeginEvent) {
        if (event.game !== this) return
        addTask({
            joined.hasTags(PTag.PLAY).forEach { player ->
                val location = player.location.apply { y -= 1 }
                val list = listClearBlock(location)
                if (list.isEmpty()) return@forEach
                belowCleared(player)
                addTask({ list.forEach { it.type = Material.AIR } }.delay(plugin, clearBlockBelowDelay))
            }
            true
        }.repeat(plugin, primaryCoolDelay, 1))
    }

    fun listClearBlock(location: Location): List<Block> = listOf(
        location.clone(),
        location.clone().add(0.0, 0.0, 0.5),
        location.clone().add(0.0, 0.0, -0.5),
        location.clone().add(.5, 0.0, 0.0),
        location.clone().add(-0.5, 0.0, 0.0),
    ).map(Location::getBlock).filter { it.type !== Material.AIR }


    fun belowCleared(player: GPlayer) = Unit

}