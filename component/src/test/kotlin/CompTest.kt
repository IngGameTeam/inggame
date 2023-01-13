import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService
import io.github.inggameteam.inggame.component.componentservice.ResourceComponentService
import io.github.inggameteam.inggame.component.createLayer
import io.github.inggameteam.inggame.component.createResource
import io.github.inggameteam.inggame.component.model.ChatAlert
import io.github.inggameteam.inggame.mongodb.MongoRepo
import io.github.inggameteam.inggame.mongodb.createMongoModule
import io.github.inggameteam.inggame.mongodb.createRepo
import io.github.inggameteam.inggame.utils.fastUUID
import org.koin.core.Koin
import org.koin.core.qualifier.named
import org.koin.dsl.koinApplication
import kotlin.system.measureTimeMillis

private const val url = "mongodb+srv://Bruce0203:F8oP5Y8USXyUfmA5@cluster0.tnbppk8.mongodb.net/?retryWrites=true&w=majority"
private const val codecPackage = "io.github.inggameteam.inggame"
const val database = "angangang"
const val component = "component"
const val player = "player"
const val resource = "resource"

val app: Koin by lazy { koinApplication {
    modules(
        createMongoModule(url, codecPackage, database),
        createRepo(component),
        createRepo(player),
        createResource(resource, component),
        createLayer(player, resource),
    )
}.koin }

fun main() {
    app.get<MongoRepo>(named(component)).apply { set(emptyList()) }
    val comp = app.get<ComponentService>(named(resource)) as ResourceComponentService
    comp.set("bed_wars", "left", ChatAlert("left the bed_wars"))
    comp.saveAll()
    val objComp = app.get<ComponentService>(named(player)) as LayeredComponentService
    val uuid = "33ff3ffc-73e5-4c15-f72a-d81700d02799".fastUUID()
    println(measureTimeMillis {
        objComp.load(uuid)
    })
    println(uuid)
    objComp.set(uuid, "game", "bed_wars")
    objComp.addParents(uuid, "bed_wars")
    objComp.unload(uuid, true)
    objComp.load(uuid)
    println(objComp[uuid, "game", String::class])

}

