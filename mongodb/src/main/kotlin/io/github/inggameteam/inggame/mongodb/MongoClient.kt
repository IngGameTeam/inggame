package io.github.inggameteam.inggame.mongodb

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.bson.UuidRepresentation
import java.util.logging.Level
import java.util.logging.Logger

fun createClient(connectionString: ConnectionString, codec: MongoCodec): MongoClient {
    val mongoLogger = Logger.getLogger("org.mongodb.driver.*")
    mongoLogger.level = Level.SEVERE
    val clientSettings = MongoClientSettings.builder()
        .uuidRepresentation(UuidRepresentation.STANDARD)
        .applyConnectionString(connectionString)
        .codecRegistry(codec.codecRegistry)
        .build()
    return MongoClients.create(clientSettings)
}
