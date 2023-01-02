package io.github.inggameteam.inggame.mongodb

import com.mongodb.MongoClientSettings
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.ClassModel
import org.bson.codecs.pojo.PojoCodecProvider

fun createCodec(codecs: Collection<Class<*>>): CodecRegistry {
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
