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
    var reversed: Boolean = false,
    var title: String = "",
    var progress: Double = 1.0,
    var color: BarColor = BarColor.WHITE,
    var style: BarStyle = BarStyle.SOLID,
    var alert: ((GPlayer) -> String)? = null
    ) {

    var viewer: GPlayerList? = null
    val bossBars = HashMap<GPlayer, BossBar>()

    fun getPersonalTitle(player: GPlayer): String {
        if (alert !== null) {
            return alert!!.invoke(player)
        } else {
            return title
        }
    }

    fun getBossBar(player: GPlayer): BossBar = bossBars.getOrElse(player) { getNewBossBar(player) }
    private fun getNewBossBar(player: GPlayer) = Bukkit.createBossBar(getPersonalTitle(player), color, style).apply { progress = this@GBar.progress; bossBars[player] =
        this
    }

    fun update(
        title: String? = null,
        progress: Double = this.progress,
        color: BarColor? = null,
        style: BarStyle? = null,
        alert: ((GPlayer) -> String)? = this.alert
    ) {
        this.progress = progress
        if (color !== null) this.color = color
        if (style !== null) this.style = style
        if (title !== null) this.title = title
        this.alert = alert
        putViewers()
        bossBars.forEach {
            it.value.apply {
                this.progress = progress
                this.color = this@GBar.color
                this.style = this@GBar.style
                setTitle(getPersonalTitle(it.key))
            }
        }

    }

    fun putViewers() {
        if (viewer != null) viewer!!.apply {
            forEach { player -> getBossBar(player).addPlayer(player.bukkit) }
            viewer = null
        }
    }

    fun removePlayer(player: GPlayer) {
        if (viewer?.isEmpty() == true) {
            viewer = null
        } else {
            viewer?.remove(player)
            getBossBar(player).removePlayer(player.bukkit)
            bossBars.remove(player)
        }
    }

    fun addPlayer(player: GPlayer) {
        if (viewer === null) {
            viewer = GPlayerList()
        }
        viewer?.add(player)
    }

    fun startTimer(runnable: () -> Unit): ITask {
        return {

            tick += adder
            update(progress = 0.0.coerceAtLeast((tick/ size).run { if (reversed) 1.0 - this else this }))
            (tick < size).apply { if (!this) runnable()}
        }.repeat(plugin, 1, 1)
    }

}