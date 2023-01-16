package io.github.inggameteam.inggame.plugin.test

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService
import io.github.inggameteam.inggame.component.model.ActionBarAlert
import io.github.inggameteam.inggame.component.model.ChatAlert
import org.koin.core.qualifier.named

@Test
class ModelSaveTest {

    init {
        val comp = PLUGIN.app.get<ComponentService>(named("test")) as LayeredComponentService
        comp.load("test", true)
        comp.set("test", "test", ChatAlert("HELLO BRUCE"))
        comp.unload("test", true)
        comp.load("test", false)
        val result = comp["test", "test", ChatAlert::class]
        println(result)
    }
}