package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.event.GameBeginEvent
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.utils.Time
import org.bukkit.event.EventHandler
import java.lang.System.currentTimeMillis

interface Recorder : Game {

    @Suppress("unused")
    @EventHandler
    fun recordOnBegin(event: GameBeginEvent) {
        if (event.game !== this) return
        playerData.keys.forEach { playerData[it]!![START_TIME] = currentTimeMillis() }
    }

    fun recordPeriod(player: GPlayer) =
        currentTimeMillis() - (playerData[player]!![START_TIME] as? Long?: currentTimeMillis())

    fun recordSeconds(player: GPlayer) =
        (recordPeriod(player)/100).toInt().toDouble().div(10)

    fun recordString(player: GPlayer) =
        Time.recordString(recordSeconds(player))

    companion object {
        const val START_TIME = "recordStartTime"
    }


}