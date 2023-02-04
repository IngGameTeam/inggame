import io.github.inggameteam.swear.Swear
import java.io.File
import java.util.regex.Pattern
import kotlin.system.measureTimeMillis

fun main() {
//    val swear = Swear(File("modules/swear/swear-api/src/main/resources/swears.json"))
//    measureTimeMillis {
//        swear.findSwear("-").apply(::println)
//    }.apply { println(this) }
//
    "안녕하세요Å방갑습니다 . 저는 ㅇ. 지오민ㅇ\"'{}abc"
        .replace(Regex("[\\d<>%.,\"'=)_(*&^\$#@!/\\]\\[}{?]"), "")
        .replace(Regex("(?!\\p{IsHangul})."), "")
        .replace(Regex("[ㅂㅈㄷㄱㅅㅛㅕㅑㅐㅔㅁㄴㅇㄹㅎㅗㅓㅏㅣㅋㅌㅊㅍㅠㅜㅡ]"), "")
        .apply(::println)
}