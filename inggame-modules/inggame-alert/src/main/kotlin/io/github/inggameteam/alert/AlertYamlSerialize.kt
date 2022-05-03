package io.github.inggameteam.alert

import io.github.inggameteam.alert.api.Alert
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.utils.YamlUtil
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import org.bukkit.configuration.ConfigurationSection
import io.github.inggameteam.utils.YamlUtil.string

object AlertYamlSerialize {
    fun alert(conf: ConfigurationSection): Alert<GPlayer> {
        if (conf.isString("chat")) return ChatAlert(mapOf("msg" to YamlUtil.string(conf, "chat")))
        if (conf.isConfigurationSection("title")) {
            val section = conf.getConfigurationSection("title")!!
            return TitleAlert(mapOf(
                "title" to string(section, "title"),
                "subTitle" to string(section, "subTitle"),
            ),
                section.getInt("fadeIn"),
                section.getInt("stay"),
                section.getInt("fadeOut")
            )
        }
        if (conf.isString("actionbar"))
            return ActionBarAlert(mapOf("msg" to string(conf, "actionbar")))
        if (conf.isConfigurationSection("component")) {
            val section = conf.getConfigurationSection("component")!!
            val events = ArrayList<AbstractEventAlert>()
            if (section.isConfigurationSection("hover")) {
                val hoverSection = section.getConfigurationSection("hover")!!
                val action = HoverEvent.Action.valueOf(string(hoverSection, "action"))
                return HoverEventAlert(mapOf("text" to string(hoverSection, "text")), action)
            }
            if (section.isConfigurationSection("click")) {
                val clickSection = section.getConfigurationSection("click")!!
                val action = ClickEvent.Action.valueOf(string(clickSection, "action"))
                return ClickEventAlert(mapOf("text" to string(clickSection, "text")), action)
            }
        }
        return EmptyAlert()
    }
}