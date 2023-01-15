package io.github.inggameteam.inggame.mongodb

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.bson.UuidRepresentation
import org.slf4j.LoggerFactory
import java.io.File

fun createClient(connectionString: ConnectionString, codec: MongoCodec): MongoClient {

    val file = File("libraries/org/slf4j/slf4j-api/1.7.32/log4j2.xml")
    file.createNewFile()
    file.writeText("""
<category name="org.mongodb.driver">
    <priority value="OFF" />
</category>
    """.trimIndent())
    val clientSettings = MongoClientSettings.builder()
        .uuidRepresentation(UuidRepresentation.STANDARD)
        .applyConnectionString(connectionString)
        .codecRegistry(codec.codecRegistry)
        .build()
    return MongoClients.create(clientSettings)
}
