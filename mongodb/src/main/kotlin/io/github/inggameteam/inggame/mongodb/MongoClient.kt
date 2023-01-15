package io.github.inggameteam.inggame.mongodb

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.bson.UuidRepresentation
import org.slf4j.LoggerFactory
import java.util.logging.Level
import java.util.logging.Logger

fun createClient(connectionString: ConnectionString, codec: MongoCodec): MongoClient {
    listOf(
        "org.mongodb.driver.cluster",
        "org.mongodb.driver.client",
    ).forEach { Logger.getLogger(it).setLevel(Level.OFF) }
    val clientSettings = MongoClientSettings.builder()
        .uuidRepresentation(UuidRepresentation.STANDARD)
        .applyConnectionString(connectionString)
        .codecRegistry(codec.codecRegistry)
        .build()
    return MongoClients.create(clientSettings)
}
