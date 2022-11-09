package io.github.inggameteam.world

import org.bukkit.World
import kotlin.system.measureTimeMillis

object WorldChunkLoader {

    fun loadChunk(world: World, xzWidth: Int) {

        val chunkWidth = xzWidth / 16
        measureTimeMillis {
            for (x in 0..chunkWidth) {
                for (z in 0..chunkWidth) {
                    val chunk = world.getChunkAt(x*16, z*16)
                    if (chunk.isLoaded) continue
                    chunk.isForceLoaded = true
                    chunk.load(true)
                    chunk.isForceLoaded = true
                }
            }
        }.apply {
            println("measureChunkLoad: $this")
        }

    }

}