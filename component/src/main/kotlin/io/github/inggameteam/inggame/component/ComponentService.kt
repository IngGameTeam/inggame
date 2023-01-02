package io.github.inggameteam.inggame.component

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.Updates
import io.github.inggameteam.inggame.mongodb.CollectionString
import io.github.inggameteam.inggame.mongodb.DatabaseString
import org.bson.BsonArray
import org.bson.BsonDocument
import org.bson.BsonDocumentWriter
import org.bson.BsonString
import org.bson.Document
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext
import org.bson.codecs.configuration.CodecRegistry
import org.koin.core.Koin
import org.koin.core.qualifier.named
import org.koin.dsl.ModuleDeclaration
import org.koin.dsl.bind
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import kotlin.concurrent.thread
import kotlin.reflect.KClass

class ComponentService(
    private val dbName: DatabaseString,
    private val colName: CollectionString,
    private val client: MongoClient,
    private val codecRegistry: CodecRegistry) {

    private var semaphore = false
    private var koins: Map<String, Koin> = HashMap()

    init {
        startWatch()
        pool()
    }

    fun getCol(): MongoCollection<Document> = client.getDatabase(dbName.dbName).getCollection(colName.colName)

    private fun startWatch() {
        thread {
            var col = getCol().watch()
            while (true) {

                col.forEach { _ ->
                    col = getCol().watch()
                    pool()
                    return@forEach
                }
            }
        }.stop()
    }

    private fun pool() {
        if (semaphore) return
        semaphore = true
        val collection = getCol()
        val components = collection.find()
        val declareModules = HashMap<String, ArrayList<ModuleDeclaration>>()
        val moduleParents = HashMap<String, List<String>>()
        components.forEach { col ->
            val doc = col.toBsonDocument()
            val idDoc = doc.getDocument("_id")
            val nameSpace = idDoc.getString("name_space").value
            moduleParents[nameSpace] = col.getList("parents", String::class.java)
            declareModules.getOrPut(nameSpace) { ArrayList() }.add {
                val valueDoc = doc.getDocument("value")
                val decode = collection.codecRegistry[Class.forName(valueDoc.getString("_t").value)]
                    .decode(valueDoc.asBsonReader(), DecoderContext.builder().checkedDiscriminator(true).build())
                single(named(idDoc.getString("key").value)) { decode } bind(decode.javaClass.kotlin)
            }
        }
        koins = declareModules.mapValues { module { it.value.forEach { module -> module.invoke(this) } } }.let { modules ->
            modules
                .mapValues { pair -> module {
                    includes(*moduleParents[pair.key]!!.map { modules[it]
                        ?: throw AssertionError("${pair.key}'s parent '$it' is not exist") }
                        .toTypedArray(), pair.value) } }
                .mapValues { module -> koinApplication { modules(module.value) }.koin }
        }
        semaphore = false
    }

    operator fun <T : Any> get(nameSpace: String, key: String, clazz: KClass<T>): T {
        val koin = koins[nameSpace]
            ?: throw AssertionError("$nameSpace namespace does not exist")
        return koin.get(clazz, named(key))
    }

    fun set(nameSpace: String, key: String, inst: Any? = null, parent: List<String>? = null) {
        val doc = Document()
        val id = BsonDocument().apply {
            this["name_space"] = BsonString(nameSpace)
            this["key"] = BsonString(key)
        }
        doc["_id"] = id
        parent?.let { doc["parents"] = BsonArray((parent).map(::BsonString)) }
        inst?.let {
            val value = BsonDocument().apply {
                val writer = BsonDocumentWriter(this)
                codecRegistry[inst.javaClass].encode(writer, inst, EncoderContext.builder().build())
            }
            doc["value"] = value
        }
        getCol().findOneAndUpdate(Filters.eq("_id", id), Updates.combine(
            Updates.setOnInsert(doc),
        ), FindOneAndUpdateOptions().upsert(true))
    }

    fun delete(nameSpace: String, key: String) {
        val doc = Document()
        val id = BsonDocument().apply {
            this["name_space"] = BsonString(nameSpace)
            this["key"] = BsonString(key)
        }
        doc["_id"] = id
        getCol().deleteOne(doc)
    }

}