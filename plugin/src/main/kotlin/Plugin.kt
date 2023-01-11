import io.github.inggameteam.inggame.component.componentservice.ResourceComponentService
import io.github.inggameteam.inggame.component.createLayer
import io.github.inggameteam.inggame.component.createResource
import io.github.inggameteam.inggame.component.delegate.NonNullDelegateImp
import io.github.inggameteam.inggame.minigame.createGameService
import io.github.inggameteam.inggame.minigame.wrapper.Server
import io.github.inggameteam.inggame.mongodb.createMongoModule
import io.github.inggameteam.inggame.mongodb.createRepo
import io.github.inggameteam.inggame.utils.IngGamePluginImp
import org.koin.core.Koin
import org.koin.core.qualifier.named
import org.koin.dsl.koinApplication
import org.koin.dsl.module

class Plugin : IngGamePluginImp() {

    private val url = "mongodb+srv://Bruce0203:F8oP5Y8USXyUfmA5@cluster0.tnbppk8.mongodb.net/?retryWrites=true&w=majority"
    private val codecPackage = "io.github.inggameteam.inggame"
    private val database = "angangang"
    private val component = "component"
    private val player = "player"
    private val resource = "resource"
    private val game = "game"

    val app: Koin by lazy { koinApplication {
        modules(
            createMongoModule(url, codecPackage, database),
            createRepo(component),
            createRepo(player),
            createResource(resource, component),
            createLayer(player, resource),
            createGameService(game),
            module {
                factory { Server(NonNullDelegateImp("server", get(named(resource)))) }
            }
        )
    }.koin }

    override fun onEnable() {
        val comp = app.get<ResourceComponentService>()
        comp.poolNameSpace()
        comp.saveNameSpace()
    }

    override fun onDisable() {
        super.onDisable()
        app.close()
    }

}