package io.github.inggameteam.alert

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class TestServer {
    companion object {
        lateinit var server: ServerMock
        lateinit var plugin: TestPlugin
    }

    @BeforeTest
    fun setUp() {
        println("setUp")
        server = MockBukkit.mock()
        plugin = MockBukkit.load(TestPlugin::class.java) as TestPlugin
    }

    @AfterTest
    fun tearDown() {
        MockBukkit.unmock()
    }

    @Test
    fun addPlayer() {
        println("addPlayer")
        val player = server.addPlayer()
        repeat(10) {
            val before = System.currentTimeMillis()
            plugin.component.send("test", plugin[player], "value")
            println(player.nextMessage())
            val after = System.currentTimeMillis()
            println(after - before)
        }
    }

}
