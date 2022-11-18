package io.github.inggameteam.mongodb.impl

import io.github.inggameteam.api.IngGamePlugin
import io.github.inggameteam.mongodb.api.MongoDBCP
import io.github.inggameteam.utils.fastToString
import org.bson.Document
import java.util.*

class GameLog(val plugin: IngGamePlugin, mongo: MongoDBCP) {
    private val client = mongo.createClient()

    fun insert(sender: UUID, message: String, time: Long) {
        client.getDatabase("statics").getCollection("chat")
            .insertOne(
                Document("sender", sender.fastToString())
                    .append("message", message)
                    .append("time", time)
            )
    }



}