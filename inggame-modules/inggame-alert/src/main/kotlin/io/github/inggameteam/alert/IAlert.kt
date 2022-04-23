package io.github.inggameteam.alert

import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.entity.Player


abstract class AlertDefs {
    abstract fun build(vararg args: Any): IAlert
}

abstract class IAlert {
    abstract fun send(player: Player)
}

class ChatDef(vararg val message: String) : AlertDefs() {
    override fun build(vararg args: Any) = object : IAlert() {
        override fun send(player: Player) {
            player.sendMessage(*message.map { it.format(*args) }.toTypedArray())
        }
    }
}

class ActionBar(val text: String) : AlertDefs() {
    override fun build(vararg args: Any) = object : IAlert() {
        override fun send(player: Player) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent(text.format(*args)))
        }
    }
}

class Title(
    val title: String, val subTitle: String,
    val fadeIn: Int, val stay: Int, val fadeOut: Int,
    ) : AlertDefs() {
    override fun build(vararg args: Any) = object : IAlert() {
        override fun send(player: Player) {
            player.sendTitle(title.format(*args), subTitle.format(*args.reversedArray()), fadeIn, stay, fadeOut)
        }
    }
}
