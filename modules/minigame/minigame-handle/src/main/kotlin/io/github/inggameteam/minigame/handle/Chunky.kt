package io.github.inggameteam.minigame.handle

import org.bukkit.Bukkit
import org.bukkit.Location
import org.popcraft.chunky.api.ChunkyAPI


class Chunky(loc: Location) {


    init {
        try {
            val chunky: ChunkyAPI? = Bukkit.getServer().servicesManager.load(ChunkyAPI::class.java)
        chunky!!.startTask(loc.world!!.name, "square", loc.x, loc.z, 1000.0, 1000.0,"concentric")
        chunky.onGenerationComplete { event -> Bukkit.getLogger().info("Generation completed for " + event.world()) }
        } catch (_: Exception) {
            println("Failed to starting Chunky")
        }

    }

}