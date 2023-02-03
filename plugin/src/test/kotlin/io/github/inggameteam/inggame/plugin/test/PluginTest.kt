package io.github.inggameteam.inggame.plugin.test

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import io.github.inggameteam.inggame.plugin.Plugin
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import kotlin.reflect.full.createInstance

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
    fun testAll() {
        "-".repeat(20).apply(::println)
        throw AssertionError()
            listOf<Class<*>>().forEach { clazz ->
                try {
                    clazz.kotlin.createInstance()
                    println("${clazz.simpleName} PASSED")
                } catch (e: Throwable) {
                    e.printStackTrace()
                    println("${clazz.simpleName} FAILED")
                }
            }
        "-".repeat(20).apply(::println)

    }


}