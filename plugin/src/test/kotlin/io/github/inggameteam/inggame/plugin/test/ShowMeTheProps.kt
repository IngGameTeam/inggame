package io.github.inggameteam.inggame.plugin.test

import io.github.inggameteam.inggame.component.PropRegistry

@Test
class ShowMeTheProps {

    init {
        PLUGIN.app.get<PropRegistry>().run {  listOf(*models.toTypedArray(), *wrappers.toTypedArray()) }
            .apply { map { it.simpleName }.apply { println(this) } }
    }
}