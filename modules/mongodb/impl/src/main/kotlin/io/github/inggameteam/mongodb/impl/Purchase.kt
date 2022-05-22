package io.github.inggameteam.mongodb.impl

import com.mongodb.client.model.Updates
import io.github.inggameteam.api.IngGamePlugin
import io.github.inggameteam.mongodb.api.MongoDBCP
import io.github.inggameteam.utils.fastToString
import org.bson.Document
import java.util.*
import kotlin.collections.HashMap

class Purchase(override val uuid: UUID, map: Map<String, Int> = mapOf()) : UUIDUser, HashMap<String, Int>(map)

class PurchaseContainer(plugin: IngGamePlugin, mongo: MongoDBCP) :
    Container<Purchase>(plugin, mongo, "user", "purchase") {

    override fun pool(uuid: UUID): Purchase {
        val uuidToString = uuid.fastToString()
        val user = col.find(Document("_id", uuidToString)).first()?.run {
            Purchase(uuid, this.mapValues { it.value as? Int ?: 0 })
        }?: Purchase(uuid)
        return user
    }

    override fun upsert(data: Purchase) {
        val document = Document("_id", data.uuid.fastToString())
        if (col.updateOne(document, data.map { Updates.set(it.key, it.value) }.toMutableList()).matchedCount == 0L)
            data.forEach { document.append(it.key, it.value) }
            col.insertOne(document)
    }

}
