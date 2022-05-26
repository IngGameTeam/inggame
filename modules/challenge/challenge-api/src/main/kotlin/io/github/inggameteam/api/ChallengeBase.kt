package io.github.inggameteam.api

import io.github.inggameteam.alert.AlertPlugin
import io.github.inggameteam.mongodb.impl.ChallengeContainer
import io.github.inggameteam.player.GPlayer

interface ChallengeBase<PLUGIN: AlertPlugin> : Holder<ChallengeContainer>, PluginHolder<PLUGIN> {

    val name: String

    val comp get() = plugin.components["challenge"]

    fun challenge(player: GPlayer) = item[player][this.name]

}