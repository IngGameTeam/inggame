package io.github.inggameteam.alert.component

import io.github.inggameteam.alert.AlertPlugin
import io.github.inggameteam.player.GPlayer

const val LANG = "lang"

object Lang {
    fun GPlayer.lang(plugin: AlertPlugin) = this[LANG]?.toString()?: plugin.defaultLanguage

}