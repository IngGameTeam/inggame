package io.github.inggameteam.inggame.minigame.handler

import io.github.inggameteam.inggame.component.delegate.getAll
import io.github.inggameteam.inggame.minigame.GameInstanceService
import io.github.inggameteam.inggame.minigame.Sector
import io.github.inggameteam.inggame.minigame.wrapper.game.Game
import org.bukkit.World
import kotlin.math.sqrt

class SectorLoader(
    val gameInstanceService: GameInstanceService
) {

    fun newAllocatable(world: World): Sector {
        val list = gameInstanceService.getAll(::Game).filter(Game::isAllocatedGame)
            .map(Game::gameSector).filter { it.worldOrNull == world }.toSet()
        val line = sqrt(list.size.toDouble()).toInt() + 1
        var x = 1
        while (x <= line) {
            var z = 1
            while (z <= line) {
                if (!list.any { it.equals(x, z) }) return Sector(x, z, world.name)
                z++
            }
            x++
        }
        return Sector(1, 1, world.name)
    }

}