package io.github.inggameteam.inggame.mongodb

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.LoggerContext
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.bson.UuidRepresentation
import org.slf4j.LoggerFactory

fun createClient(connectionString: ConnectionString, codec: MongoCodec): MongoClient {

    listOf(
        "org.mongodb.driver",
        "org.mongodb.driver.cluster",
        "org.mongodb.driver.client",
        "org.reflections"
    ).forEach {
        val logger = LoggerFactory.getLogger(MongoClient::class.java)
        logger.info("-".repeat(100))
    }
    val clientSettings = MongoClientSettings.builder()
        .uuidRepresentation(UuidRepresentation.STANDARD)
        .applyConnectionString(connectionString)
        .codecRegistry(codec.codecRegistry)
        .build()
    return MongoClients.create(clientSettings)
}
