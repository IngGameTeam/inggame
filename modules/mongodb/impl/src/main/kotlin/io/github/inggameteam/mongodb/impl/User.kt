package io.github.inggameteam.mongodb.impl

import com.mongodb.client.model.Updates
import io.github.inggameteam.mongodb.api.MongoDBCP
import io.github.inggameteam.utils.fastToString
import org.bson.Document
import org.bukkit.plugin.Plugin
import java.util.*

class User(uuid: UUID, var point: Long) : UUIDUser(uuid)

class UserContainer(plugin: Plugin, database: String, collection: String, mongo: MongoDBCP) :
    Container<User>(plugin, database, collection, mongo) {

    override fun pool(uuid: UUID): User {
        val uuidToString = uuid.fastToString()
        val user = col.find(Document("_id", uuidToString)).first()?.run {
            val point = this["point"] as? Long?: 0L
            User(uuid, point)
        }?: User(uuid, 0)
        user.point += 1
        return user
    }

    override fun upsert(user: User) {
        val document = Document("_id", user.uuid.fastToString())
        if (col.updateOne(document, Updates.set("point", user.point)).apply { println(modifiedCount) }.matchedCount == 0L)
            col.insertOne(document.append("point", user.point))
    }

}
