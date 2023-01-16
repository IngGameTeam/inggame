package io.github.inggameteam.inggame.mongodb

import com.mongodb.ConnectionString
import io.github.inggameteam.inggame.utils.ClassUtil.matchClass
import net.sf.corn.cps.CPScanner
import net.sf.corn.cps.ClassFilter
import net.sf.corn.cps.ResourceFilter
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module


fun createMongoModule(
    url: String,
    modelClasses: List<String>,
    vararg codecPackage: String,
) = module {
        single { ConnectionString(url) }
        single { MongoCodec(ArrayList<Class<out Any>>().apply {
            codecPackage
                .map {
                    CPScanner.scanClasses(
                        ResourceFilter().packageName(it),
                        ClassFilter().annotation(Model::class.java)
                    )
                }.forEach(::addAll)
            modelClasses.mapNotNull { clazz ->
                try { matchClass(codecPackage.toList(), clazz).java.apply { add(this)} }
                catch (_: Throwable) { null }
            }
            apply { map { it.simpleName }.apply(::println) }
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