package io.github.inggameteam.inggame.mongodb

import com.mongodb.ConnectionString
import io.github.inggameteam.inggame.utils.ClassRegistry
import org.bson.codecs.Codec
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf


class ClassRegistryAll(vararg clazz: KClass<*>) : ClassRegistry(*clazz)

fun createModelRegistryAll() = module(createdAtStart = true) {
    single {
        ArrayList<KClass<*>>().apply { getAll<ClassRegistry>().map { it.classes }.forEach { addAll(it) } }
            .run { ClassRegistryAll(*this.toTypedArray()) }
    }
}

fun createMongoModule(
    url: String,
) = module {
    single { ConnectionString(url) }
    single { MongoCodec(ArrayList<Class<out Any>>().apply {
        get<ClassRegistryAll>().classes.
        filter { it.java.getAnnotation(Model::class.java) !== null
                || it.isSubclassOf(Codec::class)
        }.map { it.java }.apply(::addAll)
    }) }
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
    factory(named(name)) { MongoFileRepo(fileDir, get(), get()) } bind MongoRepo::class
}