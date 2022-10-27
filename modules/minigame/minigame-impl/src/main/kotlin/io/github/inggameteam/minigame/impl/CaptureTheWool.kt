package io.github.inggameteam.minigame.impl

import io.github.inggameteam.bossbar.GBar
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.base.*
import io.github.inggameteam.player.hasTags
import io.github.inggameteam.scheduler.ITask
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.boss.BarColor

class CaptureTheWool(plugin: GamePlugin) : TeamCompetitionImpl(plugin),
    BarGame, InteractingBan, Respawn, SimpleGame, SpawnTeamPlayer {
    override val name get() = "capture-the-wool"
    override val bar by lazy { GBar(plugin) }
    override val noInteracts by lazy { listOf(Material.BLUE_STAINED_GLASS, Material.RED_STAINED_GLASS,
        Material.BLUE_WOOL, Material.RED_CONCRETE, Material.BLUE_CONCRETE
    ) }
    private val gage = comp.intOrNull("gage")?.toDouble()?: 275.0
    var blueGage = 0.0
    var redGage = 0.0

    override fun beginGame() {
        super.beginGame()
        gameTask = ITask.repeat(plugin, 1, 1, {
            var blueOn = false
            var redOn = false
            joined.hasTags(PTag.PLAY).filter {
                it.gameMode !== GameMode.SPECTATOR &&
                        it.location.add(0.0, -3.0, 0.0).block.type === Material.EMERALD_BLOCK
            }.forEach {
                val playerTeam = getPlayerTeam(it)
                if (playerTeam === PTag.RED) redOn = true
                else if (playerTeam === PTag.BLUE) blueOn = true
            }
            if (redOn && blueOn || !redOn && !blueOn) {
                fill(Material.WHITE_CONCRETE)
                bar.update(
                    color = BarColor.WHITE,
                    progress = 0.0
                )
            } else {
                if (blueGage >= gage) {
                    joined.hasTags(PTag.PLAY, PTag.RED).forEach {
                        it.damage(10000.0)
                        it.removeTag(PTag.PLAY)
                        it.addTag(PTag.DEAD)
                    }
                    requestStop()
                    return@repeat
                } else if (redGage >= gage) {
                    joined.hasTags(PTag.PLAY, PTag.BLUE).forEach {
                        it.damage(10000.0)
                        it.removeTag(PTag.PLAY)
                        it.addTag(PTag.DEAD)
                    }
                    requestStop()
                    return@repeat
                }
                if (blueOn.not() && redOn.not()) {
                    fill(Material.WHITE_CONCRETE)
                    bar.update(
                        color = BarColor.WHITE,
                        progress = 1.0
                    )
                    return@repeat
                }
                fill(if (blueOn) Material.BLUE_CONCRETE else Material.RED_CONCRETE)
                if (blueOn) blueGage++ else redGage++
                bar.update(
                    color = if (blueOn) BarColor.BLUE else BarColor.RED,
                    progress = (if (blueOn) blueGage else redGage) / gage,
                )
            }
        })
    }

    private fun fill(material: Material) {
        val l = getLocation("POINT").add(-2.0, 0.0, -2.0)
        for (x in l.blockX..l.blockX+4) for (z in l.blockZ..l.blockZ+4)
            Location(l.world, x.toDouble(), l.y, z.toDouble()).block.type = material
    }

}