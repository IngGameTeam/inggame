//package io.github.inggameteam.inggame.minigame.test
//
//import io.github.inggameteam.inggame.component.createLayer
//import io.github.inggameteam.inggame.component.createResource
//import io.github.inggameteam.inggame.component.delegate.NonNullDelegateImp
//import io.github.inggameteam.inggame.minigame.createGameService
//import GameServer
//import io.github.inggameteam.inggame.mongodb.createMongoModule
//import io.github.inggameteam.inggame.mongodb.createRepo
//import io.github.inggameteam.inggame.utils.randomUUID
//import org.koin.core.Koin
//import org.koin.core.qualifier.named
//import org.koin.dsl.koinApplication
//import org.koin.dsl.module
//
//
//private const val url = "mongodb+srv://Bruce0203:F8oP5Y8USXyUfmA5@cluster0.tnbppk8.mongodb.net/?retryWrites=true&w=majority"
//private const val codecPackage = "io.github.inggameteam.inggame"
//const val database = "angangang"
//const val component = "component"
//const val player = "player"
//const val resource = "resource"
//const val game = "game"
//
//
//val app: Koin by lazy { koinApplication {
//    modules(
//        createMongoModule(url, codecPackage, database),
//        createRepo(component),
//        createRepo(player),
//        createResource(resource, component),
//        createLayer(player, resource),
//        createGameService(game),
//        module {
//            factory { GameServer(NonNullDelegateImp("server", get(named(resource)))) }
//        }
//    )
//}.koin }
//
//fun main() {
//    val server = app.get<GameServer>()
//    server.hub = randomUUID()
//    println(server.hub)
//}