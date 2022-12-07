package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.JoinType
import io.github.inggameteam.minigame.LeftType
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.world.FaweImpl
import org.bukkit.Location
import kotlin.concurrent.thread

open class Hub(plugin: GamePlugin) : SectionalImpl(plugin), FireTicksOffOnSpawn {


    override fun stop(force: Boolean, leftType: LeftType) = Unit
    override var gameState
    get() = GameState.STOP
        set(_) {GameState.STOP}
    override val startPlayersAmount = -1
    override val startWaitingSecond = -1
    override val name: String get() = plugin.gameRegister.hubName
    override fun requestJoin(gPlayer: GPlayer, joinType: JoinType, sendMessage: Boolean) = true
    override fun unloadSector() = Unit
    override fun loadSector(key: String) {
        val height = plugin.gameRegister.sectorHeight
        val width = plugin.gameRegister.sectorWidth
        val x = width * point.x
        val z = width * point.y
        val file = getSchematicFile(key, this.name)
        val location = Location(world, x.toDouble(), height.toDouble(), z.toDouble())
        thread {
//            FaweImpl(plugin).loadChunk(location, file)
            FaweImpl(plugin).paste(location, file)
        }
    }


}
