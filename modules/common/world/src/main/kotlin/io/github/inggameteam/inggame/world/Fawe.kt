package io.github.inggameteam.inggame.world

import com.fastasyncworldedit.core.FaweAPI
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.math.BlockVector3
import org.bukkit.Bukkit
import org.bukkit.Location
import java.io.File


private val fawe get() = Bukkit.getPluginManager().getPlugin("FastAsyncWorldEdit")

fun paste(location: Location, file: File) {
    try {
        if (file.exists().not()) return
        FaweAPI.load(file).apply {

            paste(
                BukkitAdapter.adapt(location.world),
                BlockVector3.at(location.x, location.y, location.z),
                false, true, null
            )
            File(fawe!!.dataFolder, "clipboard")
                .listFiles()?.forEach { it.delete() }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
