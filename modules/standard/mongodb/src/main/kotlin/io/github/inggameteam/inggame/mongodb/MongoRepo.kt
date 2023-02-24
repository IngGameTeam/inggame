package io.github.inggameteam.inggame.mongodb

import org.bson.Document

interface MongoRepo {



    fun get(): Collection<Document>

    fun get(id: Any): Document?

    fun set(id: Any, replace: Document)

    fun delete(id: Document)

    fun set(col: Collection<Document>)

}