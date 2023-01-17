package io.github.inggameteam.inggame.mongodb

import com.mongodb.client.model.Filters
import com.mongodb.client.model.FindOneAndReplaceOptions
import org.bson.Document

class MongoRepoImpl(val col: MongoCollection) : MongoRepo {

    override fun get() = col.getCol().find().toList()

    override fun get(id: Any): Document? {
        return col.getCol().find(Filters.eq("_id", id)).firstOrNull()
    }

    override fun set(id: Any, replace: Document) {
        col.getCol().findOneAndReplace(
            Filters.eq("_id", id), replace, FindOneAndReplaceOptions().upsert(true))
    }

    override fun set(col: Collection<Document>) {
        this.col.getCol().deleteMany(Document())
        if (col.isEmpty()) return
        this.col.getCol().insertMany(col.toMutableList())
    }

    override fun delete(id: Document) {
        col.getCol().deleteOne(Filters.eq("_id", id))
    }

}