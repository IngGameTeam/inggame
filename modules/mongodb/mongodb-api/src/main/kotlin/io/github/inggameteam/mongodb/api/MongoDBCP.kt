package io.github.inggameteam.mongodb.api

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import io.github.inggameteam.api.PluginHolder
import io.github.inggameteam.player.PlayerPlugin
import java.io.File
import java.util.*
import kotlin.test.assertNotNull

interface MongoDBCP : PluginHolder<PlayerPlugin> {

    val client: MongoClient

    fun createClient(): MongoClient {
        val prop = Properties()
        val propFileName = "database.properties"
        prop.load(File(plugin.dataFolder, propFileName).inputStream())
        val url = prop["url"]?.toString().apply { assertNotNull(this, propFileName) }!!
        return MongoClients.create(url)
    }

}

