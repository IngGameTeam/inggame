package io.github.inggameteam.mongodb.impl

import io.github.inggameteam.api.IngGamePlugin
import io.github.inggameteam.mongodb.api.MongoDBCP
import io.github.inggameteam.utils.fastToString
import org.bson.Document
import java.util.UUID

class Votes(val plugin: IngGamePlugin, val mongo: MongoDBCP) {

    private val client = mongo.createClient()

    fun addVote(uniqueId: UUID, adder: Int = 1) {
        val col = client.getDatabase("user").getCollection("votes")
        if (col.updateOne(
                Document("uuid", uniqueId.fastToString()),
                Document("\$inc", Document("amount", adder))
            ).matchedCount == 0L
        ) {
            col.insertOne(Document("uuid", uniqueId).append("amount", adder))
        }
    }

    fun getVote(uniqueId: UUID): Int {
        val col = client.getDatabase("user").getCollection("votes")
        val vote = col.find(Document("uuid", uniqueId)).firstOrNull()
        if (vote === null) {
            return -1
        } else {
            col.deleteOne(Document("uuid", uniqueId.fastToString()))
        }
        return vote["amount"] as Int
    }

}