package io.github.inggameteam.mongodb.impl

import com.mongodb.client.model.Updates
import io.github.inggameteam.api.IngGamePlugin
import io.github.inggameteam.mongodb.api.Container
import io.github.inggameteam.mongodb.api.MongoDBCP
import io.github.inggameteam.mongodb.api.UUIDUser
import io.github.inggameteam.utils.fastToString
import org.bson.Document
import java.util.*

class User(override val uuid: UUID, var point: Long) : UUIDUser

class UserContainer(plugin: IngGamePlugin, mongo: MongoDBCP) :
    Container<User>(plugin, mongo, "user", "user") {

    override fun pool(uuid: UUID): User {
        val uuidToString = uuid.fastToString()
        val user = col.find(Document("_id", uuidToString)).first()?.run {
            val point = this["point"] as? Long?: 0L
            User(uuid, point)
        }?: User(uuid, 0)
        return user
    }

    override fun commit(data: User) {
        val document = Document("_id", data.uuid.fastToString())
        if (col.updateOne(document, Updates.set("point", data.point)).matchedCount == 0L)
            col.insertOne(document.append("point", data.point))
    }

}
