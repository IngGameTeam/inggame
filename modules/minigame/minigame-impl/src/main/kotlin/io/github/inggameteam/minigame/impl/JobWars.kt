package io.github.inggameteam.minigame.impl

import io.github.inggameteam.alert.Lang.lang
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.base.CompetitionImpl
import io.github.inggameteam.minigame.base.SimpleGame
import io.github.inggameteam.minigame.base.SpawnPlayer
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.hasTags
import io.github.inggameteam.scheduler.delay
import io.github.monun.invfx.InvFX
import io.github.monun.invfx.openFrame
import net.kyori.adventure.text.Component
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerMoveEvent

class JobWars(plugin: GamePlugin) : CompetitionImpl(plugin), SimpleGame, SpawnPlayer {

    companion object {
        const val PLAYER_JOB = "playerJob"
        const val MENU_OPENED = "menuOpened"
    }

    override val name get() = "job-wars"

    enum class Job(val jobName: String, val visible: Boolean = true) {
        J0("시민"),
        J1("파괴자"),
        J2("간디"),
        J3("금광석"),
        J4("테러리스트"),
        J5("교주"),
    }

    private fun job(player: GPlayer) = playerData[player]!![PLAYER_JOB] as? Job

    private fun job(player: GPlayer, job: Job) {
        playerData[player]!![PLAYER_JOB] = job
    }

    override fun beginGame() {
        super.beginGame()
        joined.hasTags(PTag.PLAY).forEach {
            playerData[it]!![MENU_OPENED] = true
            choosingMenu(it)
        }
        gameTask = { finishChoosing() }.delay(plugin, 20 * 10)
    }

    private fun spawn(player: GPlayer) {
        player.gameMode = GameMode.SURVIVAL
        val lang = player.lang(plugin)
        player.teleport(comp.stringList("locations", lang).map { getLocation(it) }.random())
        val job = job(player)?: Job.values().random()
        job.apply { player.inventory.contents = comp.inventory(jobName, lang).contents }
    }

    private fun finishChoosing() {
        joined.forEach { playerData[it]!!.remove(MENU_OPENED) }
        joined.forEach { it.closeInventory() }
        joined.forEach { spawn(it) }
        gameTask = null
    }

    @Suppress("unused")
    @EventHandler
    fun cannotMoveWhenOpeningMenu(event: PlayerMoveEvent) {
        if (isJoined(event.player) && playerData[plugin[event.player]]!![MENU_OPENED] == true)
            event.isCancelled = true
    }

    private fun choosingMenu(player: GPlayer) {
        player.openFrame(InvFX.frame(6, Component.text("직업 선택")) {
            list(2, 1, 5, 2, true, { Job.values().filter { it.visible } }) {
                transform { comp.item(it.jobName, player.lang(plugin)) }
                onClickItem { _, _, item, _ ->
                    job(player, item.first)
                    player.closeInventory()
                }
            }
        })
    }

}