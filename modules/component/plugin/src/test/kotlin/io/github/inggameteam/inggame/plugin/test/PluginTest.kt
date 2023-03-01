package io.github.inggameteam.inggame.plugin.test

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import io.github.inggameteam.inggame.plugin.Plugin
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

lateinit var SERVER: ServerMock
lateinit var PLUGIN: Plugin

object PluginTest {


    @BeforeAll
    @JvmStatic
    fun setUp() {
        SERVER = MockBukkit.mock()
        PLUGIN = MockBukkit.load(Plugin::class.java)
    }

    @AfterAll
    @JvmStatic
    fun tearDown() {
        MockBukkit.unmock()
    }

    @Test
    fun test() {
        SimpleTest().test()
    }

}