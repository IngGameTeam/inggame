import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import io.github.inggameteam.inggame.plugin.Plugin
import org.junit.After
import org.junit.Before
import org.junit.Test

class PluginTest {

    private var server: ServerMock? = null
    private var plugin: Plugin? = null

    @Before
    fun setUp() {
        server = MockBukkit.mock()
        plugin = MockBukkit.load(Plugin::class.java)
    }

    @After
    fun tearDown() {
        MockBukkit.unmock()
    }

    @Test
    fun a() {
        println(plugin)
    }


}