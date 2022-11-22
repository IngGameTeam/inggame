package io.github.inggameteam.minigame.handle

import org.bukkit.Bukkit
import org.bukkit.Location
import org.popcraft.chunky.api.ChunkyAPI


class Chunky(loc: Location) {


    init {
        try {
            val size  = 2000
            val chunky: ChunkyAPI? = Bukkit.getServer().servicesManager.load(ChunkyAPI::class.java)
        chunky!!.startTask(loc.world!!.name, "square", loc.x + size/2, loc.z + size/2, size.toDouble(), size.toDouble(),"concentric")
        } catch (_: Exception) {
            println("Failed to starting Chunky")
        }

    }

}