package io.github.inggameteam.world

import com.fastasyncworldedit.core.FaweAPI
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.math.BlockVector3
import org.bukkit.Bukkit
import org.bukkit.Location
import java.io.File
import kotlin.system.measureTimeMillis


val FAWE get() = Bukkit.getPluginManager().getPlugin("FastAsyncWorldEdit")

interface Fawe {

    fun loadChunk(location: Location, file: File)
    fun unloadChunk(location: Location, file: File)
    fun paste(location: Location, file: File)
}

open class FaweImpl : Fawe {

    override fun loadChunk(location: Location, file: File) {
        try {
            if (file.exists().not()) return
            FaweAPI.load(file).apply {
                measureTimeMillis {
                    val world = location.world!!
                    for (chunk in region.chunks) {
                        println(chunk.toString())
                        world.getChunkAt(chunk.x, chunk.z).apply {
                            isForceLoaded = true
                            load(true)
                        }
                    }
                }.apply { println("measureChunkLoadTimeMillis: $this") }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun unloadChunk(location: Location, file: File) {
/*        try {
            if (file.exists().not()) return
            FaweAPI.load(file).apply {
                measureTimeMillis {
                    val world = location.world!!
                    for (chunk in region.chunks) {
                        world.unloadChunk(chunk.x, chunk.z, false)
                    }
                }.apply { println("measureChunkUnloadTimeMillis: $this") }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }*/
    }

    override fun paste(location: Location, file: File) {
        try {
            if (file.exists().not()) return
            FaweAPI.load(file).apply {

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
