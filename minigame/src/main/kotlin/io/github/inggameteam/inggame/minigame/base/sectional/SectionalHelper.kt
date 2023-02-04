package io.github.inggameteam.inggame.minigame.base.sectional

import io.github.inggameteam.inggame.minigame.GameInstanceService
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.async
import io.github.inggameteam.inggame.utils.runNow
import io.github.inggameteam.inggame.world.paste
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.EntityType
import java.io.File
import kotlin.concurrent.thread

class SectionalHelper(
    val gameInstanceService: GameInstanceService,
    val plugin: IngGamePlugin
) {

    fun clearEntitiesToUnload(sectional: Sectional): Unit = sectional.run {
        val before = System.currentTimeMillis()
        gameSector.world.getNearbyEntities(
            Location(gameSector.world,
                gameSector.x * gameWidth.toDouble(),
                gameHeight.toDouble(),
                gameSector.y * gameWidth.toDouble()
            ), 150.0, 150.0, 150.0).apply {
            println("clearEntities(nearBy):${System.currentTimeMillis() - before}ms")
        }.forEach {
            if (it.type != EntityType.PLAYER) it.remove()
        }
        println("clearEntities(remove):${System.currentTimeMillis() - before}ms")
    }

    fun loadSector(sectional: Sectional, key: String = sectional.schematicName) {
        loadSector(sectional, sectional.gameSector.world, sectional.gameSector, key)
    }

    fun unloadSector(sectional: Sectional) {
        if (sectional.isUnloaded) return
        sectional.isUnloaded = true
        unloadSector(sectional, sectional.gameSector.world, sectional.gameSector)
    }

    private fun unloadSector(sectional: Sectional, world: World, sector: Sector): Unit = sectional.run {
        val before = System.currentTimeMillis()
        val x = sector.x * gameWidth
        val z = sector.y * gameWidth
        val file = getSchematicFile("default", "default")
        val location = Location(world, x.toDouble(), gameHeight.toDouble(), z.toDouble())
        ;{
        paste(location, file)
//            unloadChunk(location, getSchematicFile(schematicName, this.name))
        plugin.logger.info("${sectional.gameName} unloaded $sector (${System.currentTimeMillis() - before}ms)")
        ;{
        if (gameInstanceService.has(this))
            sectional.cancelGameTask()
        gameInstanceService.remove(this)
    }.runNow(plugin)
    }.async(plugin)
    }

    private fun loadSector(sectional: Sectional, world: World, sector: Sector, key: String): Unit = sectional.run {
        val x = gameWidth * sector.x
        val z = gameWidth * sector.y
        val file = getSchematicFile(key, gameName)
        val location = Location(world, x.toDouble(), gameHeight.toDouble(), z.toDouble())
        thread {
//            FaweImpl(plugin).loadChunk(location, file)
            paste(location, file)
        }
    }

    fun getSchematicFile(name: String, dir: String) =
        File(plugin.dataFolder, "schematics" + File.separator + dir + File.separator + name + ".schem")




}