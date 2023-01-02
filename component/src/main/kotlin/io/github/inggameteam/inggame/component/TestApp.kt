package io.github.inggameteam.inggame.component

import com.mongodb.ConnectionString
import io.github.inggameteam.inggame.component.model.ChatAlert
import io.github.inggameteam.inggame.mongodb.*
import org.bson.Document
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.reflections.Reflections
import kotlin.system.measureTimeMillis

class TestApp
fun main() {
    val url = "mongodb+srv://Bruce0203:d4HZ2VIQqkqd4MjP@cluster0.tnbppk8.mongodb.net/?retryWrites=true&w=majority"
    val mongoModule = module {
        single {
            createCodec(
                Reflections("io.github.inggameteam.inggame")
                    .getTypesAnnotatedWith(Model::class.java)
            )
        }
        single { DatabaseString("angangang") }
        single { ConnectionString(url) }
        singleOf(::createClient)
        factory { ComponentService(get(), CollectionString("component"), get(), get()) }
    }
    koinApplication {
        val app = modules(mongoModule).koin
        val component = app.get<ComponentService>()
        component.getCol().deleteMany(Document())
        component.set("minigame", "left", ChatAlert("left the game"))
        component.set("bed_wars", "join", ChatAlert("join the bed_wars"), arrayListOf(""))
        component.set("bed_wars", "left", ChatAlert("left the bed_wars"), arrayListOf(""))
        while(true) {
            measureTimeMillis {
                    component["bed_wars", "left", ChatAlert::class].apply(::println)
            }.apply(::println)
            Thread.sleep(1000)
        }
    }.close()
}