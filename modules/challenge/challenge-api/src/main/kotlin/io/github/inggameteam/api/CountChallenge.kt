package io.github.inggameteam.api

import io.github.inggameteam.alert.AlertPlugin
import io.github.inggameteam.api.event.ChallengeArchiveEvent
import io.github.inggameteam.player.GPlayer
import org.bukkit.Bukkit

interface CountChallenge<PLUGIN : AlertPlugin> : ChallengeBase<PLUGIN> {

    val goal: Int

    fun add(player: GPlayer, amount: Int = 1) {
         val challenge = challenge(player)
        if (challenge.data != CHALLENGED) challenge.data += amount
        if (challenge.data >= goal) {
            goal(player)
        }
    }

    fun goal(player: GPlayer) {
        challenge(player).data = CHALLENGED
        Bukkit.getPluginManager().callEvent(ChallengeArchiveEvent(player, name))
    }

    fun reset(player: GPlayer, init: Int = 0) {
        val challenge = challenge(player)
        if (challenge.data != CHALLENGED) challenge.data = init
    }


    companion object {
        const val CHALLENGED = -1
    }

}