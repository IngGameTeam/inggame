package io.github.inggameteam.mongodb.api

import com.mongodb.client.MongoCollection
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.api.IngGamePlugin
import org.bson.Document

abstract class PoolImpl<DATA>(
    plugin: IngGamePlugin,
    private val mongo: MongoDBCP,
    private val database: String,
    private val collection: String,
    ) : Pool<DATA>, HandleListener(plugin) {

    open val col: MongoCollection<Document> get() = mongo.client.getDatabase(database).getCollection(collection)

}