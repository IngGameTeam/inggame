package io.github.inggameteam.minigame.base

import io.github.inggameteam.alert.DEFAULT_DIR
import io.github.inggameteam.minigame.*
import io.github.inggameteam.minigame.base.Sectional.Companion.DEFAULT
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.scheduler.async
import io.github.inggameteam.scheduler.delay
import io.github.inggameteam.world.FaweImpl
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.util.Vector
import java.io.File
import kotlin.system.measureTimeMillis


/**
 * Among games, it is the upper class of all mini-games that require area.
 */
interface Sectional : Game {
    val schematicName: String
    val minPoint: Vector
    val maxPoint: Vector
    var isUnloaded: Boolean
    fun loadSector(key: String = schematicName)
    fun loadDefaultSector() = loadSector(schematicName)
    fun unloadSector()
    fun isInSector(location: Location): Boolean
    fun getLocation(key: String): Location
    fun getLocationOrNull(key: String): Location?
    fun getSchematicFile(name: String, dir: String): File

    companion object {
        const val DEFAULT = "default"
    }
}


abstract class SectionalImpl(plugin: GamePlugin) : GameImpl(plugin), Sectional {

    /**
     * 할당된 구역 마무리 정리 시간
     */
    override val stopWaitingTick = 20 * 5L
    override val schematicName by lazy { comp.stringListOrNull("schems", plugin.defaultLanguage)?.random()?: "default" }

    private val height get() = plugin.gameRegister.sectorHeight
    private val width get() = plugin.gameRegister.sectorWidth

    final override val minPoint: Vector
    final override val maxPoint: Vector
    override var isUnloaded = false
    init {
        if (isAllocated) {
            val vector = Vector(point.x * width, 0, point.y * width)
            val half = width / 2
            minPoint = vector.clone().add(Vector(-half, Int.MIN_VALUE, -half))
            maxPoint = vector.clone().add(Vector(half, Int.MAX_VALUE, half))
        } else {
            minPoint = Vector()
            maxPoint = Vector()
        }
    }

    override fun joinGame(gPlayer: GPlayer, joinType: JoinType): Boolean {
        return if (super.joinGame(gPlayer, joinType)) {
            if (isAllocated && joined.size == 1) loadSector()
            true
        } else false
    }

    override fun leftGame(gPlayer: GPlayer, leftType: LeftType) =
        super.leftGame(gPlayer, leftType).apply {
            if (isAllocated && joined.size == 0) {
                ;{ unloadSector() }.delay(plugin, 20 * 10)
            }
        }


    val LOCATION get() = "static"

    override fun getLocation(key: String): Location =
        comp.location(key, schematicName).run {
            toLocation(point.world).apply {
                if (tag?.contains(LOCATION) == true) return@apply
                x += width * point.x
                y += height
                z += width * point.y
            }
        }


    override fun getLocationOrNull(key: String): Location? =
        comp.locationOrNull(key, schematicName)?.run {
            toLocation(point.world).apply {
                if (tag?.contains(LOCATION) == true) return@apply
                x += width * point.x
                y += height
                z += width * point.y
            }
        }


    override fun loadSector(key: String) { { loadSector(point.world, point, key) }.async(plugin) }
    override fun unloadSector() {
        if (isUnloaded) return
        isUnloaded = true
        ;{unloadSector(point.world, point)}.async(plugin)
    }

    private fun unloadSector(world: World, sector: Sector) {
        val before = System.currentTimeMillis()
        val x = sector.x * width
        val z = sector.y * width
        val file = getSchematicFile(DEFAULT, DEFAULT_DIR)
        FaweImpl().paste(Location(world, x.toDouble(), height.toDouble(), z.toDouble()), file)
        plugin.logger.info("$name unloaded $sector (${System.currentTimeMillis() - before}ms)")
    }

    private fun loadSector(world: World?, sector: Sector, key: String) {
        val x = width * sector.x
        val z = width * sector.y
        val file = getSchematicFile(key, this.name)
        FaweImpl().paste(Location(world, x.toDouble(), height.toDouble(), z.toDouble()), file)
    }

    override fun getSchematicFile(name: String, dir: String) =
        File(plugin.dataFolder, dir + File.separator + name + ".schem")

    @EventHandler
    fun outSectionCheck(event: PlayerMoveEvent) {
        val player = event.player
        if (!isJoined(player)) return
        val to = event.to
        if (to != null && !isInSector(to)
            && !player.isOp && gameState === GameState.PLAY) event.isCancelled = true
    }

    override fun isInSector(location: Location): Boolean {
        if (gameState !== GameState.WAIT && location.toVector().isInAABB(minPoint, maxPoint)) {
            return true
        }
        return false
    }
}
