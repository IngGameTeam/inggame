package io.github.inggameteam.inggame.minigame.base.sectional

import io.github.inggameteam.inggame.utils.Helper
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.world.paste
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.EntityType
import java.io.File
import kotlin.concurrent.thread

@Helper
class SectionalHelper(
    val plugin: IngGamePlugin
) {

    fun clearEntitiesToUnload(sectional: Sectional): Unit = sectional.run {
        val before = System.currentTimeMillis()
        sector.world.getNearbyEntities(
            Location(sector.world,
                sector.x * width.toDouble(),
                height.toDouble(),
                sector.y * width.toDouble()
            ), 150.0, 150.0, 150.0).apply {
            println("clearEntities(nearBy):${System.currentTimeMillis() - before}ms")
        }.forEach {
            if (it.type != EntityType.PLAYER) it.remove()
        }
        println("clearEntities(remove):${System.currentTimeMillis() - before}ms")
    }

    fun loadSector(sectional: Sectional, key: String = sectional.schematicName) {
        loadSector(sectional, sectional.sector.world, sectional.sector, key)
    }

    fun unloadSector(sectional: Sectional) {
        unloadSector(sectional, sectional.sector.world, sectional.sector)
    }

    private fun unloadSector(sectional: Sectional, world: World, sector: Sector): Unit = sectional.run {
        val before = System.currentTimeMillis()
        val x = sector.x * width
        val z = sector.y * width
        val file = getSchematicFile("default")
        val location = Location(world, x.toDouble(), height.toDouble(), z.toDouble())
        paste(location, file)
//            unloadChunk(location, getSchematicFile(schematicName, this.name))
        plugin.logger.info("${sectional.containerName} unloaded $sector (${System.currentTimeMillis() - before}ms)")
    }

    private fun loadSector(sectional: Sectional, world: World, sector: Sector, key: String): Unit = sectional.run {
        val x = width * sector.x
        val z = width * sector.y
        val file = getSchematicFile(key)
        val location = Location(world, x.toDouble(), height.toDouble(), z.toDouble())
        thread {
//            FaweImpl(plugin).loadChunk(location, file)
            paste(location, file)
        }
    }

    private fun getSchematicFile(name: String) =
        File(plugin.dataFolder, "schematics" + File.separator + name + ".schem")




}