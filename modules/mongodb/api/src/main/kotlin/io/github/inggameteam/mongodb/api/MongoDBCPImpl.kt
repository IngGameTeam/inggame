package io.github.inggameteam.mongodb.api

import io.github.inggameteam.api.PluginHolder
import io.github.inggameteam.player.PlayerPlugin

class MongoDBCPImpl(override val plugin: PlayerPlugin) : MongoDBCP, PluginHolder<PlayerPlugin> {
    init {
        System.setProperty("org.litote.mongo.test.mapping.service", "org.litote.kmongo.jackson.JacksonClassMappingTypeService")
    }
    override val client = createClient()

}