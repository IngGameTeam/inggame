package io.github.inggameteam.command.context

import io.github.inggameteam.command.tree.ArgumentCommandNode

class ParsedArgument<S>(val name: String, val node: ArgumentCommandNode<S>)
