package io.github.inggameteam.mongodb.impl

import com.mongodb.client.model.Updates
import io.github.inggameteam.api.IngGamePlugin
import io.github.inggameteam.mongodb.api.Container
import io.github.inggameteam.mongodb.api.MongoDBCP
import io.github.inggameteam.mongodb.api.UUIDUser
import io.github.inggameteam.utils.fastToString
import org.bson.Document
import java.util.*
import kotlin.collections.ArrayList

class PurchaseList(override val uuid: UUID, val purchases: ArrayList<Purchase> = arrayListOf()) : UUIDUser {
    operator fun get(key: String) = purchases.firstOrNull { it.name == key }
        ?: Purchase(key, 0, System.currentTimeMillis()).apply { purchases.add(this) }
}

class Purchase(val name: String, var amount: Int, var lastTime: Long) {
    fun updateLastTime() { lastTime = System.currentTimeMillis() }
    override fun toString(): String {
        return "{name=$name, amount=$amount, lastTime=$lastTime}"
    }
}

class PurchaseContainer(plugin: IngGamePlugin, mongo: MongoDBCP) :
    Container<PurchaseList>(plugin, mongo, "user", "purchase") {

    override fun pool(uuid: UUID): PurchaseList {
        val uuidToString = uuid.fastToString()
        val user = col.find(Document("uuid", uuidToString)).map {
            Purchase(it["name"] as String, it["amount"] as Int, it["lastTime"] as Long)
        }.toList()
        return PurchaseList(uuid, ArrayList(user))
    }

    override fun commit(data: PurchaseList) {
        data.purchases.forEach {
            val document = Document("uuid", data.uuid.fastToString()).append("name", it.name)
            if (it.amount <= 0) {
                col.deleteOne(document)
            }
            val updateDoc = mutableListOf(
                Updates.set("amount", it.amount),
                Updates.set("lastTime", it.lastTime)
            )
            if (col.updateOne(document, updateDoc).matchedCount == 0L)
                col.insertOne(document.append("amount", it.amount).append("lastTime", it.lastTime))
        }
    }

}
