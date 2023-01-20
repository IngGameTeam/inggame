package io.github.inggameteam.inggame.mongodb

import org.bson.Document
import java.io.File

class MongoFileRepo(val file: String) : MongoRepo {

    fun getFile() = File(file)
        .apply { if (!exists()) {
            parentFile.mkdir()
            createNewFile()
            writeText("""{"_": []}""")
        }
        }
    override fun get(): Collection<Document> {
        val doc = Document.parse(getFile().readText())
        return doc.getList("_", Document::class.java)
    }

    override fun get(id: Any): Document? {
        return get().firstOrNull { it["_id"] == id }
    }

    override fun set(id: Any, replace: Document) {
        var exist = false
        get().map {
            if (it["_id"] == id) {
                exist = true
                replace.forEach(it::set)
                it
            } else it
        }.run { if (!exist) ArrayList(this).apply { add(Document().apply {
            append("_id", id)
            replace.forEach(this::set)
        }) } else this }.apply(::set)
    }

    override fun set(col: Collection<Document>) {
        getFile().writeText(Document().apply { set("_", col) }.toJson())
    }

    override fun delete(id: Document) {
        get().filterNot { id == it["_id"] }.apply(::set)
    }

}