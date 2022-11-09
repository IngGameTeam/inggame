package io.github.inggameteam.world

import org.bukkit.World
import kotlin.system.measureTimeMillis

object WorldChunkLoader {

    fun loadChunk(world: World, xzWidth: Int) {

        val chunkWidth = xzWidth / 16
        measureTimeMillis {
            for (x in 0..chunkWidth) {
                for (z in 0..chunkWidth) {
                    val before = System.currentTimeMillis()
                    val chunk = world.getChunkAt(x * 16, z * 16)
                    if (chunk.isLoaded)
                    chunk.isForceLoaded = true
                    chunk.load(true)
                    chunk.isForceLoaded = true
                    val after = System.currentTimeMillis()
                    val ms = after-before
                    println("measureChunkLoad($x, $z): ${ms}ms")
                }
            }
        }.apply {
            println("measureChunkLoad(World): $this")
        }

    }

}