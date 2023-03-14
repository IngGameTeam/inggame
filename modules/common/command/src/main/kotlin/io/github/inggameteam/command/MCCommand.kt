package io.github.inggameteam.command

import io.github.inggameteam.command.context.CommandContext
import io.github.inggameteam.command.tree.RootCommandNode
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

typealias Root = RootCommandNode<CommandSender>

open class MCCommand(val plugin: JavaPlugin, init: Root.() -> Unit)
    : CommandDispatcher<CommandSender>(init), CommandExecutor, TabCompleter{

    init {
        root.getChild().keys.forEach {
            plugin.getCommand(it)?.apply {
                try {
                    setTabCompleter(this@MCCommand)
                    setExecutor(this@MCCommand)
                } catch(_: Exception) {}
            }
        }
    }

    private fun fullCommand(label: String, args: Array<out String>) =
        if (args.isEmpty()) label else "$label ${args.joinToString(" ")}"


    @Suppress("NAME_SHADOWING")
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val prefix = "${plugin.name}:"
        val label = label.run { if (startsWith(prefix)) substring(prefix.length) else this }
        execute(fullCommand(label, args), sender)
        return true
    }

    @Suppress("NAME_SHADOWING")
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>,
    ): MutableList<String> {
        val prefix = "${plugin.name.lowercase()}:"
        val alias = alias.run { if (startsWith(prefix)) substring(prefix.length) else this }
        return tab(fullCommand(alias, args), sender).toMutableList()
    }


}

val CommandContext<CommandSender>.player get() = source as Player
val CommandContext<CommandSender>.playerOrNull get() = if (source is Player) source else null