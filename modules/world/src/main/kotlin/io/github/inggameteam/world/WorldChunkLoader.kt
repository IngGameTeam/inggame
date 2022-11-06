package io.github.inggameteam.world

import org.bukkit.World
import kotlin.system.measureTimeMillis

object WorldChunkLoader {

    fun loadChunk(world: World, xzWidth: Int) {

        val chunkWidth = xzWidth / 16
        measureTimeMillis {
            for (x in 0..chunkWidth) {
                for (z in 0..chunkWidth) {
                    val chunk = world.getChunkAt(x, z)
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