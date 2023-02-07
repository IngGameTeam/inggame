package io.github.inggameteam.inggame.mongodb

import com.mongodb.MongoClientSettings
import io.github.inggameteam.inggame.utils.Model
import org.bson.BsonArray
import org.bson.BsonDocument
import org.bson.BsonDocumentWriter
import org.bson.Document
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext
import org.bson.codecs.configuration.CodecProvider
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.ClassModel
import org.bson.codecs.pojo.PojoCodecProvider
import org.koin.core.component.KoinComponent
import kotlin.reflect.full.isSubclassOf

class MongoCodec(
    codecs: Collection<Class<*>>,
    private val decodeFunctionAll: DecodeFunctionAll,
    private val encodeFunctionAll: EncodeFunctionAll
) : KoinComponent {

    val codecRegistry = createCodec(codecs)

    fun decode(document: Any?): Any? {
        if (document === null) return null
        if (document is Document) {
            try {
                return codecRegistry[Class.forName(document.getString("_t"))]
                    .decode(
                        document.toBsonDocument().asBsonReader(),
                        DecoderContext.builder().checkedDiscriminator(true).build()
                    )
                    ?.run {
                        var v: Any = this
                        decodeFunctionAll.list.forEach { it.code.invoke(v)?.apply { v = this } }
                        if (v == this) v else decode(v)
                    }
                    ?: throw AssertionError("An error occurred while decoding Document")
            } catch (_: Throwable) {
                return document.mapValues { decode(it.value) }
            }
        } else if (document is Collection<*>) {
            return document.map { decode(it) }
        } else return document
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
        } else if (value is Map<*, *>) {
            value.mapValues { encode(it.value) }
        } else {
            var v: Any = value
            encodeFunctionAll.list.forEach { it.code.invoke(v)?.apply { v = this } }
            return if (value == v) v else encode(v)
        }
    }


    @Suppress("UNCHECKED_CAST", "DEPRECATION")
    private fun createCodec(codecs: Collection<Class<*>>): CodecRegistry {
        if (codecs.isEmpty()) {
            println("codecs Model is empty!")
        }
        val pojoCodecRegistry: CodecRegistry = CodecRegistries.fromProviders(
            *codecs.filter { it.kotlin.isSubclassOf(Codec::class) }.map {
                object : CodecProvider {
                    override fun <T : Any?> get(clazz: Class<T>?, registry: CodecRegistry?): Codec<T>? {
                        if (clazz != it) return null
                        return it.newInstance() as Codec<T>
                    }
                }
            }.toTypedArray(),
            PojoCodecProvider.builder().automatic(true)
                .apply {
                    codecs.map { clazz ->
                        ClassModel.builder(clazz).enableDiscriminator(true).build() }
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
