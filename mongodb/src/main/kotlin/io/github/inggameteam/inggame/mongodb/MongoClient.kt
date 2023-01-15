package io.github.inggameteam.inggame.mongodb

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.bson.UuidRepresentation
import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory

fun createClient(connectionString: ConnectionString, codec: MongoCodec): MongoClient {

    listOf(
        "org.mongodb.driver.cluster",
        "org.mongodb.driver.client",
    ).forEach {
        val logger = LoggerFactory.getLogger(it) as Logger
        logger.setLevel(ch.qos.logback.classic.Level.OFF);
    }
    val clientSettings = MongoClientSettings.builder()
        .uuidRepresentation(UuidRepresentation.STANDARD)
        .applyConnectionString(connectionString)
        .codecRegistry(codec.codecRegistry)
        .build()
    return MongoClients.create(clientSettings)
}
