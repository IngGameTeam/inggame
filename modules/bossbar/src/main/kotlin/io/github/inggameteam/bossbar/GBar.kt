package io.github.inggameteam.bossbar

import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.GPlayerList
import io.github.inggameteam.scheduler.ITask
import io.github.inggameteam.scheduler.repeat
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.plugin.Plugin

class GBar(
    val plugin: Plugin,
    var size: Double = 0.0,
    var tick: Int = 0,
    var adder: Int = 1,
    ) {

    val bossBar: BossBar = Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SOLID)
    var viewer: GPlayerList? = null

    fun update(
        title: String = bossBar.title,
        progress: Double = bossBar.progress,
        color: BarColor = bossBar.color,
        style: BarStyle = bossBar.style
    ) {
        bossBar.apply {
            this.progress = progress;this.color = color;this.style = style;setTitle(title)
        }
        putViewers()
    }

    fun putViewers() {
        if (viewer != null) viewer!!.map(GPlayer::bukkit).apply {
            forEach(bossBar::addPlayer)
            viewer = null
        }
    }

    fun removePlayer(player: GPlayer) {
        if (viewer?.isEmpty() == true) {
            viewer = null
        } else {
            viewer?.remove(player)
            bossBar.removePlayer(player.bukkit)
        }
    }

    fun addPlayer(player: GPlayer) {
        if (viewer === null) {
            viewer = GPlayerList()
        }
        viewer?.add(player)
    }

    fun startTimer(runnable: () -> Any): ITask {
        return {

            tick += adder
            update(progress = 0.0.coerceAtLeast(tick/ size))
            (tick < size).apply { if (!this) runnable()}
        }.repeat(plugin, 1, 1)
    }

}