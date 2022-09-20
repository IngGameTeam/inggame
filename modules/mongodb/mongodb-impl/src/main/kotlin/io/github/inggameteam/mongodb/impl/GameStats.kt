package io.github.inggameteam.mongodb.impl

import io.github.inggameteam.api.IngGamePlugin
import io.github.inggameteam.mongodb.api.MongoDBCP
import org.bson.Document

class GameStats(val plugin: IngGamePlugin, val mongo: MongoDBCP) {

    private val client = mongo.createClient()

    fun addWonAmount(game: String) {
        val col = client.getDatabase("statics").getCollection("game_stats")
        if (col.updateOne(
                Document("game", game),
                Document("\$inc", Document("wonAmount", 1))
            ).matchedCount == 0L
        ) {
            col.insertOne(Document("game", game).append("wonAmount", 1))
        }
    }

}