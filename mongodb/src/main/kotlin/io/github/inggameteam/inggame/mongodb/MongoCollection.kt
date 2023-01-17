package io.github.inggameteam.inggame.mongodb

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import org.bson.Document

class MongoCollection(
    private val dbName: DatabaseString,
    val colName: CollectionString,
    private val client: MongoClient
) {
    fun getCol(): MongoCollection<Document> = client.getDatabase(dbName.dbName).getCollection(colName.colName)
}