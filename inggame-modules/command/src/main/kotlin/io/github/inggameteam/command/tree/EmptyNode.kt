package io.github.inggameteam.command.tree

import io.github.inggameteam.command.context.CommandContextBuilder

class EmptyNode<S> : ArgumentCommandNode<S>() {
    override fun parse(context: CommandContextBuilder<S>) = Unit
}