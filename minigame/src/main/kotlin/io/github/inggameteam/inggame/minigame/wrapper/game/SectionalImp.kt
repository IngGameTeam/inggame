package io.github.inggameteam.inggame.minigame.wrapper.game

import io.github.inggameteam.inggame.component.PropWrapper
import io.github.inggameteam.inggame.component.delegate.Wrapper
import io.github.inggameteam.inggame.component.model.LocationModel
import io.github.inggameteam.inggame.minigame.GameState
import io.github.inggameteam.inggame.minigame.Sector
import org.bukkit.util.Vector


@PropWrapper
class SectionalImp(wrapper: Wrapper) : Game by GameImp(wrapper), Sectional {

    override var gameSector: Sector by default { Sector(0, 0) }
    override val isAllocatedGame: Boolean get() = !gameSector.equals(0, 0)
//    /**
//     * 할당된 구역 마무리 정리 시간
//     */
//    override val stopWaitingTick = 84600L * 20L
    override val schematicName: String by nonNull
    override val schematicLocations: HashMap<String, HashMap<String, LocationModel>> by nonNull

    override var minPoint: Vector by nonNull
    override var maxPoint: Vector by nonNull
    override var isUnloaded = false
    override val gameWidth: Int by nonNull
    override val gameHeight: Int by nonNull

    fun initPoints() {
        if (isAllocatedGame) {
            val vector = Vector(gameSector.x * gameWidth, 0, gameSector.y * gameWidth)
            val half = gameWidth / 2
            minPoint = vector.clone().add(Vector(-half, Int.MIN_VALUE, -half))
            maxPoint = vector.clone().add(Vector(half, Int.MAX_VALUE, half))
        } else {
            minPoint = Vector()
            maxPoint = Vector()
        }
    }

    override fun getLocation(key: String): org.bukkit.Location =
        getLocationOrNull(key)?: throw AssertionError("$key location is not exists")


    override fun getLocationOrNull(key: String): org.bukkit.Location? =
        schematicLocations[schematicName]?.get(key)?.run {
            toLocation(gameSector.world).apply {
                if (isRelative) return@apply
                x += gameWidth * gameSector.x
                y += gameHeight
                z += gameWidth * gameSector.y
            }
        }


    override fun isInSector(location: org.bukkit.Location): Boolean {
        if (gameState !== GameState.WAIT && location.toVector().isInAABB(minPoint, maxPoint)) {
            return true
        }
        return false
    }

}