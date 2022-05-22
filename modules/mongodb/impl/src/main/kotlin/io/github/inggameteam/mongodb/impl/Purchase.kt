package io.github.inggameteam.mongodb.impl

import com.mongodb.client.model.Updates
import io.github.inggameteam.mongodb.api.MongoDBCP
import io.github.inggameteam.utils.fastToString
import org.bson.Document
import org.bukkit.plugin.Plugin
import java.util.*

class Purchase(uuid: UUID, var point: Long) : UUIDUser(uuid)

class PurchaseContainer(plugin: Plugin, mongo: MongoDBCP) :
    Container<Purchase>(plugin, "user", "purchase", mongo) {

    override fun pool(uuid: UUID): Purchase {
        val uuidToString = uuid.fastToString()
        val user = col.find(Document("_id", uuidToString)).first()?.run {
            val point = this["point"] as? Long?: 0L
            Purchase(uuid, point)
        }?: Purchase(uuid, 0)
        user.point += 1
        return user
    }

    override fun upsert(data: Purchase) {
        val document = Document("_id", data.uuid.fastToString())
        if (col.updateOne(document, Updates.set("point", data.point)).apply { println(modifiedCount) }.matchedCount == 0L)
            col.insertOne(document.append("point", data.point))
    }

}
