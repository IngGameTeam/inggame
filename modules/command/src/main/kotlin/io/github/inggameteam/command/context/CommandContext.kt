package io.github.inggameteam.command.context

open class CommandContext<S>(
    val source: S, val command: String) {
    val argument = ArrayList<ParsedArgument<S>>()
    val label get() = if (argument.isEmpty()) "" else argument[0].name
    val args
        get() =
            if (argument.isEmpty()) ArrayList()
            else argument.subList(1, argument.size).map { it.name }.toList()
}
