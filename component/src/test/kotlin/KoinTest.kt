import com.mongodb.ConnectionString
import io.github.inggameteam.inggame.component.model.Alert
import io.github.inggameteam.inggame.component.model.Location
import io.github.inggameteam.inggame.mongodb.DatabaseString
import io.github.inggameteam.inggame.mongodb.Model
import io.github.inggameteam.inggame.mongodb.createClient
import io.github.inggameteam.inggame.mongodb.createCodec
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.reflections.Reflections
import kotlin.reflect.KClass
import kotlin.system.measureTimeMillis

data class GameName(val name: String) {
    override fun toString() = name
}

interface GameComponentHelper {
    operator fun <T : Any> get(key: String, clazz: KClass<T>): T
    fun getString(key: String): String
    fun getStringList(key: String): String
    fun getDouble(key: String): Double
    fun getFloat(key: String): Float
    fun getInt(key: String): Int
    fun getLocation(key: String): Location
    fun getAlert(key: String): Alert
    fun getItemStack(key: String): ItemStack
    fun getInventory(key: String): Inventory

}

class GameComponentHelperImp(
    val gameName: GameName,
) : GameComponentHelper {
    override operator fun <T : Any> get(key: String, clazz: KClass<T>): T {
//        return component[gameName.name, key, clazz]
        TODO()
    }

    override fun getString(key: String): String {
        TODO("Not yet implemented")
    }

    override fun getStringList(key: String): String {
        TODO("Not yet implemented")
    }

    override fun getDouble(key: String): Double {
        TODO("Not yet implemented")
    }

    override fun getFloat(key: String): Float {
        TODO("Not yet implemented")
    }

    override fun getInt(key: String): Int {
        TODO("Not yet implemented")
    }

    override fun getLocation(key: String): Location {
        TODO("Not yet implemented")
    }

    override fun getAlert(key: String): Alert {
        TODO("Not yet implemented")
    }

    override fun getItemStack(key: String): ItemStack {
        TODO("Not yet implemented")
    }

    override fun getInventory(key: String): Inventory {
        TODO("Not yet implemented")
    }


}

interface BaseGame : GameComponentHelper {

}

class BaseGameImp(helper: GameComponentHelper) : BaseGame, GameComponentHelper by helper {

}



interface Sectional : BaseGame {

}

class SectionalImp(baseGame: BaseGame) : Sectional, BaseGame by baseGame {
    override fun getLocation(key: String): Location {
        println("Sectional getLocation!")
        return Location("")
    }
    init { println("SectionalImp instanced")}
}

interface SpawnPlayer {
    fun spawn() {
        println("SpawnPlayer")
    }

}

class SpawnPlayerImp(sectional: Sectional) : SpawnPlayer, Sectional by sectional {
    init { println("SpawnPlayer instanced")}
    override fun spawn() {
        println("SpawnPlayer")
    }
}

interface RandomSpawnPlayer : SpawnPlayer

class RandomSpawnPlayerImp : RandomSpawnPlayer {
    override fun spawn() {
        println("RandomSpawnPlayer")
    }
}

interface LeaveWhenClickLeaveItem {
    fun a()
}

class LeaveWhenClickLeaveItemImp(baseGame: BaseGame) : BaseGame by baseGame, LeaveWhenClickLeaveItem {
    override fun a() {
        TODO("Not yet implemented")
    }

}

class SimpleGame {

}

val url = "mongodb+srv://Bruce0203:d4HZ2VIQqkqd4MjP@cluster0.tnbppk8.mongodb.net/?retryWrites=true&w=majority"
val mongoModule = module {
    single { createCodec(Reflections("io.github.inggameteam.inggame").getTypesAnnotatedWith(Model::class.java)) }
    single { DatabaseString("angangang") }
    single { ConnectionString(url) }
    singleOf(::createClient)
//    single { ComponentService(get(), CollectionString("component"), get(), get()) }
}

val minigameModule = module {
    singleOf(::BaseGameImp) bind BaseGame::class
    singleOf(::SectionalImp) bind Sectional::class
    singleOf(::SpawnPlayerImp) bind SpawnPlayer::class
    singleOf(::LeaveWhenClickLeaveItemImp) bind LeaveWhenClickLeaveItem::class
    singleOf(::RandomSpawnPlayerImp) bind RandomSpawnPlayer::class
    singleOf(::GameComponentHelperImp) bind GameComponentHelper::class
    single { GameName("testGame") }
}
fun main() {
    var koinApplication = koinApplication {
        modules(mongoModule, minigameModule)
    }
    measureTimeMillis{
        repeat(10000) {
            koinApplication.koin.get<Sectional>().getLocation("")
            koinApplication.koin.get<RandomSpawnPlayer>().spawn()
            koinApplication.koin.get<SpawnPlayer>().spawn()
            koinApplication.koin.close()
            koinApplication = koinApplication { modules(mongoModule, minigameModule)}
        }
    }.apply(::println)
}