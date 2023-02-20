package io.github.inggameteam.inggame.plugin.test

import org.bukkit.plugin.Plugin
import org.junit.jupiter.api.Test

class SimpleTest {

    @Test
    fun test() {
        println(SERVER.pluginManager.plugins.map { it.name })
    }

}