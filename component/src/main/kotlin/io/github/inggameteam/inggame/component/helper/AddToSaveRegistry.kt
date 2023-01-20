package io.github.inggameteam.inggame.component.helper

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.SaveComponentService
import io.github.inggameteam.inggame.utils.IngGamePlugin

class AddToSaveRegistry(componentService: ComponentService, plugin: IngGamePlugin) {

    init {
        println("AddToSaveRegistry")
        if (componentService is SaveComponentService) {
            plugin.addSaveEvent { componentService.saveAll() }
        } else {
            throw AssertionError("an error occurred while add to save registry")
        }
    }

}