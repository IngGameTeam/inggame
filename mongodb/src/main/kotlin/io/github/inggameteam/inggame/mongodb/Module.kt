package io.github.inggameteam.inggame.mongodb

import com.mongodb.ConnectionString
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import kotlin.reflect.KClass


fun createModelRegistryAll() = module(createdAtStart = true) {
    single {
        ArrayList<KClass<*>>().apply { getAll<ModelRegistry>().map { it.models }.forEach { addAll(it) } }
            .run { ModelRegistryAll(*this.toTypedArray()) }
    }
}

fun createMongoModule(
    url: String,
) = module {
    single { ConnectionString(url) }
    single { MongoCodec(ArrayList<Class<out Any>>().apply {
        get<ModelRegistryAll>().models.filter { it.java.getAnnotation(Model::class.java) !== null }
            .map { it.java }.apply(::addAll)
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
    factory(named(name)) { MongoFileRepo(fileDir) } bind MongoRepo::class
}