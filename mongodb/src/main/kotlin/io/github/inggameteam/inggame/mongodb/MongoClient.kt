package io.github.inggameteam.inggame.mongodb

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.bson.UuidRepresentation
import org.bson.codecs.configuration.CodecRegistry

fun createClient(connectionString: ConnectionString, codecRegistry: CodecRegistry): MongoClient {
    val clientSettings = MongoClientSettings.builder()
        .uuidRepresentation(UuidRepresentation.STANDARD)
        .applyConnectionString(connectionString)
        .codecRegistry(codecRegistry)
        .build()
    return MongoClients.create(clientSettings)
}
