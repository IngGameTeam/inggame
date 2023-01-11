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
    codecPackage: String,
    databaseString: String
) = module {
        single { ConnectionString(url) }
        single { MongoCodec(Reflections(codecPackage).getTypesAnnotatedWith(Model::class.java)) }
        single { DatabaseString(databaseString) }
        singleOf(::createClient)
        factoryOf(::MongoCollection)
        factoryOf(::MongoRepoImpl) bind MongoRepo::class
}

fun createRepo(name: String) = module {
    factory(named(name)) {
        MongoRepoImpl(MongoCollection(get(), CollectionString(name), get()))
    } bind MongoRepo::class
}


fun Module.createFileRepo(name: String, fileDir: String) {
    factory(named(name)) { MongoFileRepo(fileDir) } bind MongoRepo::class
}