package io.github.inggameteam.inggame.mongodb

import com.mongodb.MongoClientSettings
import org.bson.BsonArray
import org.bson.BsonDocument
import org.bson.BsonDocumentWriter
import org.bson.Document
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.ClassModel
import org.bson.codecs.pojo.PojoCodecProvider

class MongoCodec(codecs: Collection<Class<*>>) {

    val codecRegistry = createCodec(codecs)

    fun decode(document: Any?): Any? {
        if (document === null) return null
        if (document is Document) {
            println("-".repeat(200))
            document.keys.forEach { key ->
                val obj = document[key]
                if (obj is Collection<*>) {
                    document[key] = obj.map { decode(it) }
                } else if (obj is Document) {
                    document[key] = codecRegistry[Class.forName(obj.getString("_t"))]
                        .decode(
                            obj.toBsonDocument().asBsonReader(),
                            DecoderContext.builder().checkedDiscriminator(true).build()
                        )
                        ?: throw AssertionError("An error occurred while decoding Document")
                }
            }
        }
        return document
    }

    fun encode(value: Any?): Any? {
        if (value === null) return null
        return if (value.javaClass.getAnnotation(Model::class.java) !== null) {
            val document = BsonDocument()
            val writer = BsonDocumentWriter(document)
            codecRegistry[value.javaClass].encode(writer, value, EncoderContext.builder().build())
            return fromBson(document)
        } else if (value is Collection<*>) {
            BsonArray(value.map {
                (encode(it) as? Document)?.run(::toBson)
            }.toMutableList())
        } else value
    }


    private fun createCodec(codecs: Collection<Class<*>>): CodecRegistry {
        if (codecs.isEmpty()) {
            println("codecs Model is empty!")
        }
        val pojoCodecRegistry: CodecRegistry = CodecRegistries.fromProviders(
            PojoCodecProvider.builder().automatic(true)
                .apply {
                    codecs.map { ClassModel.builder(it).enableDiscriminator(true).build() }
                        .forEach(this::register)
                }.build()
        )
        return CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry)
    }

    fun fromBson(bson: BsonDocument): Document {
        return codecRegistry[Document::class.java].decode(bson.asBsonReader(), DecoderContext.builder().build())
    }

    fun toBson(document: Document): BsonDocument {
        return document.toBsonDocument(BsonDocument::class.java, codecRegistry)
    }


}
