package io.github.inggameteam.minigame.base

import io.github.inggameteam.alert.DEFAULT_DIR
import io.github.inggameteam.minigame.*
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


/**
 * 미니게임 중, 구역 할당이 필요한 모든 미니게임의 상위 클래스이다.
 */
abstract class Sectional(plugin: GamePlugin, point: Sector) : GameImpl(plugin, point) {

    companion object {
        const val DEFAULT = "default"

    }

    /**
     * 할당된 구역 마무리 정리 시간
     */
    override val stopWaitingTick = 20 * 60L * 10

    private val height get() = plugin.gameRegister.sectorHeight
    private val width get() = plugin.gameRegister.sectorWidth

    private val minPoint: Vector
    private val maxPoint: Vector
    init {
        val vector = Vector(point.x * width, 0, point.y * width)
        val half = width / 2
        minPoint = vector.clone().add(Vector(-half, Int.MIN_VALUE, -half))
        maxPoint = vector.clone().add(Vector(half, height, half))
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
                { unloadSector() }.delay(plugin, 20 * 10)

            }
        }

    open fun loadSector() { { loadSector(point.world, point, DEFAULT) }.async(plugin) }
    open fun unloadSector() { {unloadSector(point.world!!, point)}.async(plugin) }

    fun unloadSector(world: World, sector: Sector) {
//        val before = System.currentTimeMillis()
        val file = getFile(DEFAULT, DEFAULT_DIR)
        val sizeAsDouble = width.toDouble()
        val x = width * sector.x - (sizeAsDouble / 2).toInt()
        val z = width * sector.y - (sizeAsDouble / 2).toInt()
        val d = 50
        for (dx in x..x+width step d) for (dz in z..z+width step d)
            FaweImpl().paste(Location(world, dx.toDouble(), height.toDouble(), dz.toDouble()), file)
//        println("$sector Done in ${System.currentTimeMillis() - before}ms")
    }

    fun loadSector(world: World?, sector: Sector, name: String) {
        val x = width * sector.x
        val z = width * sector.y
        FaweImpl().paste(Location(world, x.toDouble(), height.toDouble(), z.toDouble()), getFile(name))
    }

    private fun getFile(name: String, dir: String = this.name) =
        File(plugin.dataFolder, dir + File.separator + name + ".schem")


    fun getLocation(name: String) = comp.location[name].run {
        Location(point.world,
            x + width * point.x,
            y + height,
            z + width * point.y,
            yaw, pitch)
    }

    @EventHandler
    fun outSectionCheck(event: PlayerMoveEvent) {
        val player = event.player
        if (!isJoined(player)) return
        if (event.to != null && !isInSector(event.to!!) && !player.isOp && gameState === GameState.PLAY) event.isCancelled = true
    }

    open fun isInSector(location: Location): Boolean {
        if (gameState !== GameState.WAIT && location.toVector().isInAABB(minPoint, maxPoint)) {
            return true
        }
        return false
    }

}
