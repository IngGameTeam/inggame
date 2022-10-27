package io.github.inggameteam.minigame.impl

import io.github.inggameteam.alert.Lang.lang
import io.github.inggameteam.minigame.*
import io.github.inggameteam.minigame.base.*
import io.github.inggameteam.minigame.event.GPlayerDeathEvent
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.hasTags
import io.github.inggameteam.scheduler.ITask
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.util.Vector

class ColorMatch(plugin: GamePlugin) : SimpleGame, CompetitionImpl(plugin),
        NoBlockBreak, NoBlockPlace, NoDamage, NoItemPickup, NoItemDrop, SpawnPlayer
{
    override val name get() = "color-match"
    lateinit var bossBar: BossBar
    var stage = 0
    var startTime = 75
    val addr = 7
    var time = -1
    val stageTime get() = startTime - addr * stage
    val stageEnum get() = Stage.values()[stage.coerceAtMost(Stage.values().size - 1)]

    override fun beginGame() {
        super.beginGame()
        updateColor()
        updateStageBar()
        joined.forEach {
            it.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 55555, 1))
        }
        time = stageTime
        gameTask = ITask.repeat(plugin, 1, 1, {
            if (time <= 0) {
                startTime -= 6
                startTime = 1.coerceAtLeast(startTime)
                time = startTime
                checkColor()
            } else time--
            bossBar.progress = 0.0.coerceAtLeast(time.toDouble() / stageTime)
            joined.map(GPlayer::bukkit).filter{!bossBar.players.contains(it)}.forEach(bossBar::addPlayer)
        })
    }

    override fun joinGame(gPlayer: GPlayer, joinType: JoinType): Boolean {
        return if(super.joinGame(gPlayer, joinType)) {
            if (gameState == GameState.PLAY) bossBar.addPlayer(gPlayer.bukkit)
            true
        } else false
    }

    override fun leftGame(gPlayer: GPlayer, leftType: LeftType): Boolean {
        return super.leftGame(gPlayer, leftType).apply {
            if (::bossBar.isInitialized) bossBar.removePlayer(gPlayer.bukkit)
        }
    }

    private fun updateStageBar() {
        val stage = stageEnum
        if (!::bossBar.isInitialized) bossBar = Bukkit.createBossBar("", stage.barColor, stage.barStyle)
        else {
            bossBar.color = stage.barColor
            bossBar.style = stage.barStyle
        }
    }

    private fun updateColor() {
        joined.hasTags(PTag.PLAY).forEach { p ->
            comp.item(generateSingleHex(), p.lang(plugin)).apply {
                val inventory = p.inventory
                for (i in 0..8) inventory.setItem(i, this)
            }
        }
    }

    private fun generateSingleHex() =
        Integer.toHexString(listOf(
            0,
            1,
            4,
            10,
            12,
            13,
            14,
            15,
        ).random())
//        Integer.toHexString((FastMath.random() * 15).toInt())

    private fun isMatch(gPlayer: GPlayer): Boolean {
        val location = gPlayer.location
        return listOf(
            location.clone(),
            location.clone().add(Vector(0, -1, 0)),
            location.clone().add(Vector(0, -2, 0)),
            location.clone().add(Vector(0, -3, 0)))
            .any { gPlayer.inventory.contains(it.block.type) }
    }

    private fun checkColor() {
        val playersToDie = joined.hasTags(PTag.PLAY).filterNot(::isMatch).toList()
        playersToDie.forEach { it.apply { addTag(PTag.DEAD) } }
        requestStop()
        if (gameState == GameState.PLAY) {
            playersToDie.forEach { it.apply { removeTag(PTag.PLAY) } }
            playersToDie.forEach { Bukkit.getPluginManager().callEvent(GPlayerDeathEvent(it))}
            updateStageBar()
            updateColor()
        }
    }

    enum class Stage(val barColor: BarColor, val barStyle: BarStyle = BarStyle.SOLID) {
        S1(BarColor.WHITE),
        S2(BarColor.WHITE),
        S3(BarColor.GREEN),
        S4(BarColor.GREEN),
        S5(BarColor.BLUE),
        S6(BarColor.BLUE),
        S7(BarColor.YELLOW),
        S8(BarColor.YELLOW),
        S9(BarColor.PINK),
        S10(BarColor.PINK),
        S11(BarColor.PURPLE),
        S12(BarColor.PURPLE),
        S13(BarColor.RED),
        S14(BarColor.RED),
    }


}