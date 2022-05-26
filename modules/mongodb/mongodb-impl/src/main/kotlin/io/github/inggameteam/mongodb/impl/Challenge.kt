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

class ChallengeList(override val uuid: UUID, val challenges: ArrayList<Challenge> = arrayListOf()) : UUIDUser {
    operator fun get(key: String) = challenges.firstOrNull { it.name == key }
        ?: Challenge(key, 0).apply { challenges.add(this) }
}

class Challenge(val name: String, var data: Int) {
    override fun toString(): String {
        return "{name=$name, data=$data}"
    }
}

class ChallengeContainer(plugin: IngGamePlugin, mongo: MongoDBCP) :
    Container<ChallengeList>(plugin, mongo, "user", "challenge") {

    override fun pool(uuid: UUID): ChallengeList {
        val uuidToString = uuid.fastToString()
        val user = col.find(Document("uuid", uuidToString)).map {
            Challenge(it["name"] as String, it["data"] as Int)
        }.toList()
        return ChallengeList(uuid, ArrayList(user))
    }

    override fun commit(data: ChallengeList) {
        data.challenges.forEach {
            if (it.data == 0) return@forEach
            val document = Document("uuid", data.uuid.fastToString()).append("name", it.name)
            val updateDoc = mutableListOf(
                Updates.set("data", it.data),
            )
            if (col.updateOne(document, updateDoc).matchedCount == 0L)
                col.insertOne(document.append("data", it.data))
        }
    }

}
