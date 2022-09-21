package io.github.inggameteam.mongodb.impl

import com.mongodb.client.model.Updates
import io.github.inggameteam.api.IngGamePlugin
import io.github.inggameteam.mongodb.api.Container
import io.github.inggameteam.mongodb.api.MongoDBCP
import io.github.inggameteam.mongodb.api.UUIDUser
import io.github.inggameteam.utils.fastToString
import org.bson.Document
import java.util.*

class ChallengeList(override val uuid: UUID, val challenges: ArrayList<Challenge> = arrayListOf()) : UUIDUser {
    operator fun get(key: String) = challenges.firstOrNull { it.name == key }
        ?: Challenge(key, 0, System.currentTimeMillis()).apply { challenges.add(this) }
}

class Challenge(val name: String, var data: Int, var lastTime: Long) {
    override fun toString(): String {
        return "{name=$name, amount=$data, lastTime=$lastTime}"
    }
}

class ChallengeContainer(plugin: IngGamePlugin, mongo: MongoDBCP) :
    Container<ChallengeList>(plugin, mongo, "user", "challenge") {

    override fun pool(uuid: UUID): ChallengeList {
        val uuidToString = uuid.fastToString()
        val user = col.find(Document("uuid", uuidToString)).map {
            Challenge(it["name"] as String, it["data"] as Int, it["lastTime"] as? Long?: 0L)
        }.toList()
        return ChallengeList(uuid, ArrayList(user))
    }

    override fun commit(data: ChallengeList) {
        data.challenges.forEach {
            if (it.data == 0) return@forEach
            val document = Document("uuid", data.uuid.fastToString()).append("name", it.name)
            val updateDoc = mutableListOf(
                Updates.set("data", it.data),
                Updates.set("lastTime", it.lastTime),
            )
            if (col.updateOne(document, updateDoc).matchedCount == 0L)
                col.insertOne(document.append("data", it.data).append("lastTime", it.lastTime))
        }
    }

}
