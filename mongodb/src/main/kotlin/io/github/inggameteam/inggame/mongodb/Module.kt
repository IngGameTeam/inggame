package io.github.inggameteam.inggame.mongodb

import com.mongodb.ConnectionString
import io.github.inggameteam.inggame.utils.ClassRegistry
import io.github.inggameteam.inggame.utils.Model
import org.bson.codecs.Codec
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

data class EncodeFunction(val code: (Any) -> Any?)
data class DecodeFunction(val code: (Any) -> Any?)
data class DecodeFunctionAll(val list: List<DecodeFunction>)
data class EncodeFunctionAll(val list: List<EncodeFunction>)
class ClassRegistryAll(vararg clazz: KClass<*>) : ClassRegistry(*clazz)

fun createRegistryAll() = module(createdAtStart = true) {
    factory { EncodeFunctionAll(getAll()) }
    factory { DecodeFunctionAll(getAll()) }
    factory {
        ClassRegistryAll(*getAll<ClassRegistry>()
            .let { arrayListOf<KClass<*>>().apply { it.forEach { addAll(it.classes) } } }.toTypedArray())
    }
}

fun createMongoModule(
    url: String,
) = module {
    single { ConnectionString(url) }
    singleOf(::MongoCodec)
    single { DatabaseString(get<ConnectionString>().database
        ?: throw AssertionError("database is not specified in the url")) }
    singleOf(::createClient)
    factoryOf(::MongoCollection)
    factoryOf(::MongoRepoImpl) bind MongoRepo::class
}

fun createRepo(name: String, collection: String) = module {
    factory(named(name)) {
        MongoRepoImpl(MongoCollection(get(), CollectionString(collection), get()))
    } bind MongoRepo::class
}


fun createFileRepo(name: String, fileDir: String) = module {
    factory(named(name)) { MongoFileRepo(fileDir, get()) } bind MongoRepo::class
}