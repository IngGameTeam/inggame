package io.github.inggameteam.world

import com.fastasyncworldedit.core.FaweAPI
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.extent.Extent
import com.sk89q.worldedit.math.BlockVector3
import org.bukkit.Bukkit
import org.bukkit.Location
import java.io.File
import kotlin.math.max
import kotlin.math.min
import kotlin.system.measureTimeMillis


val FAWE get() = Bukkit.getPluginManager().getPlugin("FastAsyncWorldEdit")

interface Fawe {
    fun paste(location: Location, file: File)
}

open class FaweImpl : Fawe {

    override fun paste(location: Location, file: File) {
        try {
            if (file.exists().not()) return
            FaweAPI.load(file).apply {
                measureTimeMillis {
                    val minX = this.origin.x
                    val maxX = minX + region.maximumPoint.x
                    val minY = this.origin.z
                    val maxY = minY + region.maximumPoint.z
                    println(minX)
                    println(maxX)
                    println(minY)
                    println(maxY)
                    println((this as Extent).javaClass.simpleName)
                    for (x in min(minX, maxX)..max(minX, maxX) step 16) {
                        for (y in min(minY, maxY)..max(minY, maxY) step 16) {
                            regenerateChunk(x, y, null, null)
                        }
                    }
                }.apply { println("measureChunkLoadTimeMillis: $this") }

                paste(
                    BukkitAdapter.adapt(location.world),
                    BlockVector3.at(location.x, location.y, location.z),
                    false, true, null
                )
                File(FAWE!!.dataFolder, "clipboard")
                    .listFiles()?.forEach { it.delete() }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
