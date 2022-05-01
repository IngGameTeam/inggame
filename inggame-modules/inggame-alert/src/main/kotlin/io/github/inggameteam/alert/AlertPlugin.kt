package io.github.inggameteam.alert

import io.github.inggameteam.alert.component.Component
import io.github.inggameteam.api.IngGamePlugin

interface AlertPlugin : IngGamePlugin {
    val components: HashMap<String, Component>
    val component: Component
}