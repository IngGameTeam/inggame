package io.github.inggameteam.test

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
    }

}
