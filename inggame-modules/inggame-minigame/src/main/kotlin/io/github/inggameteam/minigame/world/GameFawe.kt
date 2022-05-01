package io.github.inggameteam.minigame.world

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.utils.IntVector
import io.github.inggameteam.world.Fawe
import io.github.inggameteam.world.FaweImpl
import org.bukkit.Location
import org.bukkit.World
import java.io.File

class GameFawe(val plugin: GamePlugin) : FaweImpl() {

    fun unloadSector(world: World, sector: IntVector, gameSize: IntVector) {
/*
            val before = System.currentTimeMillis()
            val size = Game.SIZE.toDouble()
            val loc = Location(world, sector.x * size, Game.HEIGHT.toDouble(), sector.z * size)
            val aLoc = loc.clone().add(-size / 2, .0, -size / 2).toVector().divide(Vector(16, 1, 16))
            val bLoc = loc.clone().add(size / 2, .0, size / 2).toVector().divide(Vector(16, 1, 16))
            var count = 0
            for (x in aLoc.blockX..bLoc.blockX) for (z in aLoc.blockZ..bLoc.blockZ) {
                world.unloadChunk(x, z, false)
                count++
            }
            println("$sector Done in ${System.currentTimeMillis() - before}ms ($count chunks)")
*/
        val before = System.currentTimeMillis()
        val file = getFile(Fawe.DEFAULT)
        val sizeAsDouble = gameSize.x.toDouble()
        val x = gameSize.x * sector.x - (sizeAsDouble / 2).toInt()
        val z = gameSize.x * sector.y - (sizeAsDouble / 2).toInt()
        val d = 50
        for (dx in x..x+gameSize.x step d) for (dz in z..z+gameSize.x step d)
            paste(Location(world, dx.toDouble(), gameSize.y.toDouble(), dz.toDouble()), file)
        println("$sector Done in ${System.currentTimeMillis() - before}ms")
    }

    fun loadSector(world: World?, sector: IntVector, name: String, gameSize: IntVector) {
        val x = gameSize.x * sector.x
        val z = gameSize.x * sector.y
        paste(Location(world, x.toDouble(), gameSize.y.toDouble(), z.toDouble()), getFile(name))
    }

    fun getFile(name: String): File {
        return File(plugin.dataFolder, Fawe.SCHEMATIC_DIR + File.separator + name + ".schem")
    }
}