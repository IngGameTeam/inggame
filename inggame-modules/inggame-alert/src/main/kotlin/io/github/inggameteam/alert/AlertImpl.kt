package io.github.inggameteam.alert

import io.github.inggameteam.alert.api.Alert
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.entity.Player
import java.util.*

fun Map<String, String>.format(args: Array<out Any>) = map { it.value.format(*args) }.toTypedArray()

class ChatAlert(map: Map<String, String>) : Alert<Player>(map) {
    override fun send(sender: UUID?, t: Player, args: Array<out Any>) {
        t.sendMessage(sender, *map.format(args))
    }
}

class TitleAlert(
    map: Map<String, String>,
    val fadeIn: Int = map["fadeIn"]!!.toInt(),
    val stay: Int = map["stay"]!!.toInt(),
    val fadeOut: Int = map["fadeOut"]!!.toInt(),
) : Alert<Player>(map) {


    override fun send(sender: UUID?, t: Player, args: Array<out Any>) {
        t.sendTitle(map["title"]!!.format(*args), map["subTitle"]!!.format(*args), fadeIn, stay, fadeOut)
    }
}

class ActionBarAlert(map: Map<String, String>) : Alert<Player>(map) {
    override fun send(sender: UUID?, t: Player, args: Array<out Any>) {
        t.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent(map.values.first().format(*args)))
    }
}

abstract class AbstractEventAlert(
    map: Map<String, String>,
): Alert<Player>(map) {
    override fun send(sender: UUID?, t: Player, args: Array<out Any>) {
        t.spigot().sendMessage(TextComponent(map.values.first().format(*args)).apply {
            val reversedArgs = args.clone().apply { reverse() }
            event(this, reversedArgs)
        })
    }
    abstract fun event(comp: TextComponent, args: Array<out Any>)
}

class HoverEventAlert(map: Map<String, String>, private val action: HoverEvent.Action) : AbstractEventAlert(map) {
    override fun event(comp: TextComponent, args: Array<out Any>) {
        comp.hoverEvent = HoverEvent(action, ComponentBuilder(map.values.last().format(*args)).create())
    }
}

class ClickEventAlert(map: Map<String, String>, private val action: ClickEvent.Action) : AbstractEventAlert(map) {
    override fun event(comp: TextComponent, args: Array<out Any>) {
        comp.clickEvent = ClickEvent(action, map.values.last().format(*args))
    }
}

class EmptyAlert : Alert<Player>(Collections.emptyMap()) {
    override fun send(sender: UUID?, t: Player, args: Array<out Any>) {

    }
}