package io.github.inggameteam.inggame.minigame.base.sectional

import io.github.inggameteam.inggame.component.Handler.Companion.isHandler
import io.github.inggameteam.inggame.minigame.component.GameInstanceService
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.world.WorldGenerator
import kotlin.math.sqrt

class SectorLoader(
    private val gameInstanceService: GameInstanceService,
    private val plugin: IngGamePlugin
) {

    private fun loadWorld(world: String) {
        plugin.server.getWorld(world)?: run {
            WorldGenerator.generateWorld(world) {}
            plugin.server.getWorld(world)
        }
    }

    fun newAllocatable(worldString: String): Sector {
        loadWorld(worldString)
        val list = gameInstanceService.getAll(::SectionalImp)
            .filter { it.isHandler(SectionalHandler::class) }
            .filter(SectionalImp::isAllocatedGame)
            .map(SectionalImp::sector)
            .filter { it.worldOrNull?.name == worldString }.toSet()
        val line = sqrt(list.size.toDouble()).toInt() + 1
        var x = 1
        while (x <= line) {
            var z = 1
            while (z <= line) {
                if (!list.any { it.equals(x, z) }) return Sector(x, z, worldString)
                z++
            }
            x++
        }
        return Sector(1, 1, worldString)
    }

}