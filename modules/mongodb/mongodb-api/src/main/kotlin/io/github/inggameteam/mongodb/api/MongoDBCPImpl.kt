package io.github.inggameteam.mongodb.api

import io.github.inggameteam.api.PluginHolder
import io.github.inggameteam.player.PlayerPlugin
import java.util.logging.Level
import java.util.logging.Logger

class MongoDBCPImpl(override val plugin: PlayerPlugin) : MongoDBCP, PluginHolder<PlayerPlugin> {
    init {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE)
        System.setProperty("org.litote.mongo.test.mapping.service", "org.litote.kmongo.jackson.JacksonClassMappingTypeService")
    }
    override val client = createClient()

}