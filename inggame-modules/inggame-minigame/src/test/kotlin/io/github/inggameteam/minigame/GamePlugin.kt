package io.github.inggameteam.minigame

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class TestServer {
    init { inst = this }
    companion object { lateinit var inst: TestServer }
    private var server: ServerMock? = null
    private var plugin: TestPlugin? = null

    @BeforeTest
    fun setUp() {
        server = MockBukkit.mock()
        plugin = MockBukkit.load(TestPlugin::class.java) as TestPlugin
    }

    @AfterTest
    fun tearDown() {
        MockBukkit.unmock()
    }

}
