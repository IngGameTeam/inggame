package io.github.inggameteam.alert

import io.github.inggameteam.alert.tree.CompDir
import io.github.inggameteam.alert.tree.Components
import io.github.inggameteam.player.PlayerPlugin

interface AlertPlugin : PlayerPlugin {
    val defaultLanguage: String
    val components: Components
    val component: CompDir
}
