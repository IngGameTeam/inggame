package io.github.inggameteam.inggame.minigame.base.sectional

import io.github.inggameteam.inggame.component.model.LocationModel
import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.minigame.base.game.Game
import io.github.inggameteam.inggame.minigame.base.game.GameImp
import io.github.inggameteam.inggame.minigame.base.game.GameState
import org.bukkit.util.Vector


class SectionalImp(wrapper: Wrapper) : Game by GameImp(wrapper), Sectional {

    override var sector: Sector by default { Sector(0, 0) }
    override val isAllocatedGame: Boolean
        get() = !sector.equals(0, 0)
    override val schematicName: String by nonNull
    override val locations: HashMap<String, LocationModel> by nonNull
    override var center: Vector by nonNull
    override var minPoint: Vector by nonNull
    override var maxPoint: Vector by nonNull
    override var unloadingSemaphore = false
    override val width: Int by nonNull
    override val height: Int by nonNull

    fun initPoints() {
        if (isAllocatedGame) {
            val vector = Vector(sector.x * width, 0, sector.y * width)
            val half = width / 2
            center = vector.clone().add(Vector(0, height, 0))
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
            toLocation(sector.world).apply {
                if (!isRelative) return@apply
                x += width * sector.x
                y += height
                z += width * sector.y
            }
        }


    override fun isInSector(location: org.bukkit.Location): Boolean {
        if (gameState !== GameState.WAIT && location.toVector().isInAABB(minPoint, maxPoint)) {
            return true
        }
        return false
    }

}