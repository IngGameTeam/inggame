package io.github.inggameteam.inggame.component.helper

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.SaveComponentService
import io.github.inggameteam.inggame.utils.IngGamePlugin

class AddToSaveRegistry(componentService: ComponentService, plugin: IngGamePlugin) {

    init {
        if (componentService is SaveComponentService) {
            plugin.addSaveEvent { componentService.saveAll() }
            println("${componentService.name} is savable")
        } else {
//            throw AssertionError("an error occurred while add to save registry")
        }
    }

}