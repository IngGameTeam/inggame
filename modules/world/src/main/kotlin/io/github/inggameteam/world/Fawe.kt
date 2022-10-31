package io.github.inggameteam.world

import com.fastasyncworldedit.core.FaweAPI
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.math.BlockVector3
import io.papermc.lib.PaperLib
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.plugin.Plugin
import java.io.File
import kotlin.system.measureTimeMillis


val FAWE get() = Bukkit.getPluginManager().getPlugin("FastAsyncWorldEdit")

interface Fawe {

    fun loadChunk(location: Location, file: File)
    fun unloadChunk(location: Location, file: File)
    fun paste(location: Location, file: File)
}

open class FaweImpl(val plugin: Plugin) : Fawe {

    override fun loadChunk(location: Location, file: File) {
        try {
            if (file.exists().not()) return
            FaweAPI.load(file).apply {
                measureTimeMillis {
                    for (addX in minimumPoint.x..maximumPoint.x)
                        for (addY in minimumPoint.y..maximumPoint.y)
                        {
                            val world = location.world!!
                            PaperLib.getChunkAtAsync(world, location.blockX + addX, location.blockZ + addY, false)
                        }
                }.apply { println("measureChunkLoadTimeMillis: $this") }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun unloadChunk(location: Location, file: File) {
        try {
            if (file.exists().not()) return
            FaweAPI.load(file).apply {
                measureTimeMillis {
                    for (addX in minimumPoint.x..maximumPoint.x)
                        for (addY in minimumPoint.y..maximumPoint.y)
                        {
                            val world = location.world!!
                            PaperLib.getChunkAtAsync(world, location.blockX + addX, location.blockZ + addY, false).get().apply {
                                if (this.isLoaded) {
                                    unload(false)
                                }
                            }
                        }
                }.apply { println("measureChunkUnloadTimeMillis: $this") }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
