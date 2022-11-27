package io.github.inggameteam.minigame.handle

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.Sector
import io.github.inggameteam.scheduler.repeat
import org.bukkit.Bukkit

class ChunkForceLoader(val plugin: GamePlugin) {

    init {
        val length = 1
        var x = 0
        var y = 0
        ;{
            plugin.gameRegister.worldName.forEach { worldName ->
                loadChunkSector(Sector(x, y, Bukkit.getWorld(worldName)))
            }
            if (x <= length) {
                x++
                true
            } else if (y > length) {
                false
            } else {
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
        var x = min.x
        var y = min.y
        ;{
            world.getChunkAt(x, y).apply {
                load(false)
                isForceLoaded = true
            }
            if (x <= max.x) {
                x += 16
                true
            } else if(y > max.y) {
                false
            } else {
                x = 0
                y += 16
                true
            }
        }.repeat(plugin, 1L, 1L)
    }

}