package io.github.inggameteam.inggame.plugin.test

import io.github.inggameteam.inggame.component.delegate.Wrapper
import org.reflections.Reflections

@Test
class ReflectionTest {
    init {
        println(Reflections("io.github.inggameteam.inggame").getSubTypesOf(Wrapper::class.java))

    }
}