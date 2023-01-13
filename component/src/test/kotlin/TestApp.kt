import com.mongodb.ConnectionString
import io.github.inggameteam.inggame.component.componentservice.ResourceComponentService
import io.github.inggameteam.inggame.component.componentservice.ResourcesComponentServiceImp
import io.github.inggameteam.inggame.component.model.ChatAlert
import io.github.inggameteam.inggame.mongodb.*
import io.github.inggameteam.inggame.utils.fastUUID
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.reflections.Reflections
import java.util.*
import kotlin.system.measureTimeMillis

class TestApp
fun main() {
    val url = "mongodb+srv://Bruce0203:F8oP5Y8USXyUfmA5@cluster0.tnbppk8.mongodb.net/?retryWrites=true&w=majority"
    val mongoModule = module {
        single { MongoCodec(Reflections("io.github.inggameteam.inggame").getTypesAnnotatedWith(Model::class.java)) }
        single { ConnectionString(url) }
        single { DatabaseString("angangang") }
        single { CollectionString("component") }
        singleOf(::createClient)
        singleOf(::MongoCollection)
        singleOf(::MongoRepoImpl) bind MongoRepo::class
//        single { MongoFileRepo("/Users/ijong-won/IdeaProjects/mongodb-pojo-prototype/test.txt") } bind MongoRepo::class
        factoryOf(::ResourcesComponentServiceImp) bind ResourceComponentService::class
    }
    koinApplication {
        val app = modules(mongoModule).koin
//        app.get<MongoCollection>().getCol().deleteMany(Document())
        app.get<MongoRepo>().get()
//        measureTimeMillis {
        val component = app.get<ResourceComponentService>()
            component.poolNameSpace()
//    }.apply(::println)
        repeat(1000) {
            val uuid = UUID.randomUUID()
            val uuid2 = UUID.randomUUID()
            component.set(uuid, "left", ChatAlert("left the game"))
            component.set(uuid2, "join", ChatAlert("join the bed_wars"))
            component.setParents("uuid", arrayListOf(uuid))
            component.set("bed_wars", "left", ChatAlert("left the bed_wars"))
        }
        component.getNameSpaces().apply(::println)
        component.saveAll()
        app.get<MongoRepo>().get().forEach(::println)
        while(true) {
            measureTimeMillis {
                repeat(100) {
                    component["0fd8dfce-b85c-4252-8d6c-3f0157095635".fastUUID(), "left", ChatAlert::class]//.apply(::println)
                }
            }.apply(::println)
            Thread.sleep(2  )
        }
    }.close()
}

