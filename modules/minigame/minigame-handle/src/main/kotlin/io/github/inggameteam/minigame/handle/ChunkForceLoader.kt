package io.github.inggameteam.minigame.handle

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.Sector
import io.github.inggameteam.scheduler.repeat

class ChunkForceLoader(val plugin: GamePlugin) {

    init {
        val length = 5
        var x = 0
        var y = 0
        ;{
            if (x <= length) {
                loadChunkSector(Sector(x, y))
                x++
                true
            } else if (y >= length) {
                false
            } else {
                loadChunkSector(Sector(x, y))
                x = 0
                y++
                true
            }
        }.repeat(plugin, 1L, 1L)
    }

    private fun loadChunkSector(sector: Sector) {
        val width = plugin.gameRegister.sectorWidth
        val halfWidth = width/2
        val world = sector.world
        val center = Sector(sector.x * width, sector.y * width)
        val min = Sector(center.x - halfWidth, center.y - halfWidth)
        val max = Sector(center.x + halfWidth, center.y + halfWidth)
        for (x in min.x until max.x) for (y in min.y until max.y) {
            world.getChunkAt(x, y).apply {
                load(false)
                isForceLoaded = true
            }
        }
    }

}