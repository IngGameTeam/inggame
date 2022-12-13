package io.github.inggameteam.mongodb.impl

import io.github.inggameteam.api.IngGamePlugin
import io.github.inggameteam.mongodb.api.MongoDBCP
import io.github.inggameteam.utils.fastToString
import org.bson.Document
import org.bukkit.entity.Player
import java.lang.System.currentTimeMillis
import java.util.*
import kotlin.collections.HashMap
import kotlin.concurrent.thread

class UserLog(val plugin: IngGamePlugin, mongo: MongoDBCP) {
    private val client = mongo.createClient()

    fun insert(uuid: UUID, type: String, time: Long, map: HashMap<String, Any>) {
        thread {
            client.getDatabase("statics").getCollection("user_log")
                .insertOne(
                    Document("uuid", uuid.fastToString())
                        .append("type", type)
                        .append("Time", time)
                        .append("info", map)
                )
        }
    }

    fun insert(uuid: UUID, type: String, time: Long = currentTimeMillis(), block: HashMap<String, Any>.() -> Unit) =
        insert(uuid, type, time, hashMapOf<String, Any>().apply(block))

    fun insert(uuid: Player, type: String, time: Long = currentTimeMillis(), block: HashMap<String, Any>.() -> Unit) =
        insert(uuid.uniqueId, type, time, hashMapOf<String, Any>().apply(block))


}