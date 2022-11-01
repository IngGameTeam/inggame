import io.github.inggameteam.swear.Swear
import java.io.File
import kotlin.system.measureTimeMillis

fun main() {
    val swear = Swear(File("modules/swear/swear-api/src/main/resources/swears.json"))
    measureTimeMillis {
        swear.findSwear("-").apply(::println)
    }.apply { println(this) }

}