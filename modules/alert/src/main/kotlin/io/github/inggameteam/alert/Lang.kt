package io.github.inggameteam.alert

import io.github.inggameteam.player.GPlayer
import org.bukkit.entity.Player

const val LANG = "lang"

object Lang {
    fun GPlayer.lang(plugin: AlertPlugin) = this[LANG]?.toString()?: plugin.defaultLanguage
    fun Player.lang(plugin: AlertPlugin) = plugin[this][LANG]?.toString()?: plugin.defaultLanguage
    fun <T> LangDir<T>.comp(key: String, player: GPlayer, plugin: AlertPlugin) = comp(key, player.lang(plugin))

}