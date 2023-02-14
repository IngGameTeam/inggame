package io.github.inggameteam.command.context

import io.github.inggameteam.command.StringReader

class CommandContextBuilder<S>(source: S, command: String) : CommandContext<S>(source, command) {
    val reader = StringReader()
}
