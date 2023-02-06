package io.github.inggameteam.inggame.minigame.base.sectional

import io.github.inggameteam.inggame.component.delegate.Wrapper
import io.github.inggameteam.inggame.component.model.LocationModel
import io.github.inggameteam.inggame.minigame.base.game.Game
import io.github.inggameteam.inggame.minigame.base.game.GameImp
import io.github.inggameteam.inggame.minigame.base.game.GameState
import org.bukkit.util.Vector


class SectionalImp(wrapper: Wrapper) : Game by GameImp(wrapper), Sectional {

    override var gameSector: Sector by default { Sector(0, 0) }
    override val isAllocatedGame: Boolean get() = !gameSector.equals(0, 0)
    override val schematicName: String by nonNull
    override val locations: HashMap<String, LocationModel> by nonNull
    override var center: Vector by nonNull
    override var minPoint: Vector by nonNull
    override var maxPoint: Vector by nonNull
    override var isUnloaded = false
    override val gameWidth: Int by nonNull
    override val gameHeight: Int by nonNull

    fun initPoints() {
        if (isAllocatedGame) {
            val vector = Vector(gameSector.x * gameWidth, 0, gameSector.y * gameWidth)
            val half = gameWidth / 2
            center = vector.clone().add(Vector(0, gameHeight, 0))
            minPoint = vector.clone().add(Vector(-half, Int.MIN_VALUE, -half))
            maxPoint = vector.clone().add(Vector(half, Int.MAX_VALUE, half))
        } else {
            center = Vector()
            minPoint = Vector()
            maxPoint = Vector()
        }
    }

    override fun getLocation(name: String): org.bukkit.Location =
        getLocationOrNull(name)?: throw AssertionError("$name location is not exists")


    override fun getLocationOrNull(name: String): org.bukkit.Location? =
        locations["$schematicName/$name"]?.run {
            toLocation(gameSector.world).apply {
                if (!isRelative) return@apply
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