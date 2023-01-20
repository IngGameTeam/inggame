package io.github.inggameteam.inggame.mongodb

import com.mongodb.MongoClientSettings
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

    fun decode(value: Any): Any {
        return if (value is Document) {
            codecRegistry[Class.forName(value.getString("_t"))]
                .decode(value.toBsonDocument().asBsonReader(),
                    DecoderContext.builder().checkedDiscriminator(true).build())
                ?: throw AssertionError("An error occurred while decoding Document")
        } else value
    }

    fun encode(value: Any): Any {
        return if (value.javaClass.getAnnotation(Model::class.java) !== null) {
            println(value.javaClass)
            val document = BsonDocument()
            val writer = BsonDocumentWriter(document)
            codecRegistry[value.javaClass].encode(writer, value, EncoderContext.builder().build())
            return fromBson(document)
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
