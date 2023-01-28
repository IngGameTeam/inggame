package io.github.inggameteam.inggame.component.view

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun createEditorRegistry() = module(createdAtStart = true) {
    singleOf(::EditorRegistry)
}
