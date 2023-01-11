import org.bson.Document

fun main() {
//    Document().apply {
//        set("parent", Document().apply { set("child", "value") })
//    }.get("parent", Document::class).apply(::println)
    Document().apply {
        set("apple", ArrayList<String?>(100).apply { add(null); add(null); add("alskdjf")})
    }.get("apple").apply(::println)
}