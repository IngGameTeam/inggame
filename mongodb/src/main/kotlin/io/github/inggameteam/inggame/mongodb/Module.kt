package io.github.inggameteam.inggame.mongodb

import com.mongodb.ConnectionString
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import org.reflections.Reflections

fun createMongoModule(
    url: String,
    vararg codecPackage: String,
) = module {
        single { ConnectionString(url) }
        single { MongoCodec(Reflections(codecPackage).getTypesAnnotatedWith(Model::class.java)) }
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