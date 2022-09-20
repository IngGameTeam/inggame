package io.github.inggameteam.minigame.handle

import io.github.inggameteam.alert.AlertPlugin
import io.github.inggameteam.alert.Lang.lang
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.api.PluginHolder
import io.github.inggameteam.api.event.ChallengeArchiveEvent
import org.bukkit.event.EventHandler

class AnnounceChallengeArchive(override val plugin: AlertPlugin) : HandleListener(plugin), PluginHolder<AlertPlugin> {

    private val comp get() = plugin.components["challenge"]

    @Suppress("unused")
    @EventHandler
    fun onChallengeArchive(event: ChallengeArchiveEvent) {
        val type = comp.stringOrNull(event.name + "-type", plugin.defaultLanguage)
            ?.run { ChallengeType.valueOf(this) }?: ChallengeType.TARGET
        plugin.playerRegister.values.forEach {
            val lang = it.lang(plugin)
            val langName = comp.string(event.name, lang)
            val info = comp.string(event.name + "-info", lang)
            comp.send(type.toString(), it, event.player, langName, info)
        }
    }


}

enum class ChallengeType {
    NORMAL, EPIC, TARGET;
}