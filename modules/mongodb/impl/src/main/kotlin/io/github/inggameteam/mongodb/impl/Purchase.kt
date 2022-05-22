package io.github.inggameteam.mongodb.impl

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
            Purchase(uuid, this.filter { it.key != "_id" }.mapValues { it.value as? Int ?: 0 })
        }?: Purchase(uuid)
        return user
    }

    override fun upsert(data: Purchase) {
        val document = Document("_id", data.uuid.fastToString())
        val updateDoc = Document()
        data.forEach { document.append(it.key, it.value) }
        if (updateDoc.isEmpty()) return
        if (col.updateOne(document, updateDoc).matchedCount == 0L)
            col.insertOne(document)
    }

}
