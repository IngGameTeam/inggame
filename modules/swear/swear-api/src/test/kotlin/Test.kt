import io.github.inggameteam.swear.Swear
import java.io.File
import kotlin.system.measureTimeMillis

fun main() {
    println("ㅅ1ㅂ".replace(Regex("ㅅㅂ", setOf(RegexOption.IGNORE_CASE, RegexOption.UNIX_LINES)), ""))
    val swear = Swear(File("/Users/ijong-won/IdeaProjects/inggame/modules/swear/src/test/resources/swears.json"))
    measureTimeMillis {
        swear.findSwear("ㅅㅂ")
    }.apply { println(this) }

}