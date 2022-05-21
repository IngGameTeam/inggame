package io.github.inggameteam.minigame.impl

import io.github.inggameteam.bossbar.GBar
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.base.*
import io.github.inggameteam.player.hasTags
import io.github.inggameteam.scheduler.ITask
import org.bukkit.Location
import org.bukkit.entity.Minecart
import kotlin.random.Random

class TakeTheCart(plugin: GamePlugin) : SimpleGame, CompetitionImpl(plugin), BarGame, SpawnPlayer, NoDamage {
    override val name get() = "take-the-cart"
    override val bar = GBar(plugin)
    var gage = 100

    override fun beginGame() {
        super.beginGame()
        setupCart()
        gameTask = ITask.repeat(plugin, 1, 1, {
            if (gage < 0) {
                gage = 100
                workCart()
            } else gage--
        })
    }


    fun setupCart() {
        val pos1 = getLocation("pos1")
        val pos2 = getLocation("pos2")
        repeat((1 until joined.hasTags(PTag.PLAY).count()).count()) {
            pos1.world!!.spawn(Location(
                pos1.world,
                Random.nextDouble(pos1.x, pos2.x),
                pos1.y,
                Random.nextDouble(pos1.z, pos2.z),
            ), Minecart::class.java) {
                it.addScoreboardTag(CART_TAG)
                it.customName = "나를 타세요"
                it.isInvulnerable = true
            }
        }

    }

    fun workCart() {
        val playersToDie = joined.hasTags(PTag.PLAY).filter { it.vehicle == null }.toList()
        playersToDie.forEach { it.apply { addTag(PTag.DEAD) } }
        stopCheck()
        playersToDie.forEach { it.apply { removeTag(PTag.PLAY) } }
        if (gameState == GameState.PLAY) {
            playersToDie.forEach { it.damage(10000.0) }
            point.world.getNearbyEntities(getLocation("start"), 20.0, 20.0, 20.0)
                .filter { it.scoreboardTags.contains(CART_TAG) }
                .forEach {it.remove() }
            setupCart()
        }
    }

    companion object {
        const val CART_TAG = "CartStealGameCart"
    }
}