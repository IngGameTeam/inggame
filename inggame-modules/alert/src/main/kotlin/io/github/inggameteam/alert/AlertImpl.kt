package io.github.inggameteam.alert

import io.github.inggameteam.alert.api.Alert
import io.github.inggameteam.player.GPlayer
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import java.util.*
import kotlin.test.assertFailsWith

fun String.assertFormat(vararg args: Any): String {
    assertFailsWith<MissingFormatArgumentException>() {
        return format(*args)
    }
    return "'$this' Format specifier '%s' args=${args.map { it.toString() }}"
}

fun Map<String, String>.format(args: Array<out Any>) = map { it.value.assertFormat(*args) }.toTypedArray()

class ChatAlert(map: Map<String, String>) : Alert<GPlayer>(map) {
    override fun send(sender: UUID?, t: GPlayer, args: Array<out Any>) {
        t.sendMessage(sender, *map.format(args))
    }
}

class TitleAlert(
    map: Map<String, String>,
    val fadeIn: Int = map["fadeIn"]!!.toInt(),
    val stay: Int = map["stay"]!!.toInt(),
    val fadeOut: Int = map["fadeOut"]!!.toInt(),
) : Alert<GPlayer>(map) {


    override fun send(sender: UUID?, t: GPlayer, args: Array<out Any>) {
        t.sendTitle(map["title"]!!.assertFormat(*args), map["subTitle"]!!.assertFormat(*args), fadeIn, stay, fadeOut)
    }
}

class ActionBarAlert(map: Map<String, String>) : Alert<GPlayer>(map) {
    override fun send(sender: UUID?, t: GPlayer, args: Array<out Any>) {
        t.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent(map.values.first().assertFormat(*args)))
    }
}

abstract class AbstractEventAlert(
    map: Map<String, String>,
): Alert<GPlayer>(map) {
    override fun send(sender: UUID?, t: GPlayer, args: Array<out Any>) {
        t.spigot().sendMessage(TextComponent(map.values.first().assertFormat(*args)).apply {
            val reversedArgs = arrayOf(*args)
            reversedArgs.reverse()
            event(this, reversedArgs)
        })
    }
    abstract fun event(comp: TextComponent, args: Array<out Any>)
}

class HoverEventAlert(map: Map<String, String>, private val action: HoverEvent.Action) : AbstractEventAlert(map) {
    override fun event(comp: TextComponent, args: Array<out Any>) {
        comp.hoverEvent = HoverEvent(action, ComponentBuilder(map.values.last().assertFormat(*args)).create())
    }
}

class ClickEventAlert(map: Map<String, String>, private val action: ClickEvent.Action) : AbstractEventAlert(map) {
    override fun event(comp: TextComponent, args: Array<out Any>) {
        comp.clickEvent = ClickEvent(action, map.values.last().assertFormat(*args))
    }
}

class EmptyAlert : Alert<GPlayer>(Collections.emptyMap()) {
    override fun send(sender: UUID?, t: GPlayer, args: Array<out Any>) {

    }
}