import io.github.inggameteam.command.CommandDispatcher

data class CommandSender(val name: String)

fun main() {
    val dispatcher = CommandDispatcher<CommandSender> {
        command("party") {

            thenExecute("create") { println("You create a new party") }

            then("invite") {
                tab { listOf("Jimmy", "Sam", "Charlie") }

                execute { println("You invited ${args[1]}!") }
            }

        }
        command("show") {
            thenExecute("myname") {
                println(source.name)
            }
        }
    }
    dispatcher.execute("party create Jimmy", CommandSender("Bruce"))
}