package io.github.inggameteam.command.tree

open class CommandNode<S> {
    protected val children: HashMap<String, ArgumentCommandNode<S>> = LinkedHashMap()

    fun getChild() = children.toMutableMap()

    fun addChild(name: String, commandNode: ArgumentCommandNode<S>) {
        children[name] = commandNode
    }

}
