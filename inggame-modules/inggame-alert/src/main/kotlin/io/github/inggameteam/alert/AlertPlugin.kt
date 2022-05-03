package io.github.inggameteam.alert

import io.github.inggameteam.alert.component.Component
import io.github.inggameteam.player.PlayerPlugin

interface AlertPlugin : PlayerPlugin {
    val defaultLanguage: String
    val components: HashMap<String, Component>
    val component: Component
}
