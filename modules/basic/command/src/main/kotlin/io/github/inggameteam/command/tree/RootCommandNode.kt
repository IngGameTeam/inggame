package io.github.inggameteam.command.tree

import io.github.inggameteam.command.context.CommandContext
import io.github.inggameteam.command.context.CommandContextBuilder

open class RootCommandNode<S> : ArgumentCommandNode<S>() {

    fun command(vararg names: String, init: ArgumentCommandNode<S>.() -> Unit) {
        names.forEach { addChild(it, ArgumentCommandNode<S>().apply(init)) }
    }

    fun commandExecute(vararg arg: String, init: CommandContext<S>.() -> Unit) = command(*arg) { execute(init) }

    fun parse(command: String, source: S) = CommandContextBuilder(source, command).apply { parse(this) }

    private val CommandContext<S>.lastNode get() = argument.lastOrNull { it.node !is EmptyNode }?.node

    fun execute(context: CommandContext<S>) {
        val node = context.lastNode?: return
        node.execute(context)
    }

    fun tab(context: CommandContext<S>): Collection<String> {
        val node = context.lastNode?: return ArrayList()
        return node.tab(context)
    }

}
