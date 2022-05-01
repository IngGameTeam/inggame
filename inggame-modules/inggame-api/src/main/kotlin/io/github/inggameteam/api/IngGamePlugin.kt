package io.github.inggameteam.api

import org.bukkit.plugin.Plugin
import java.util.*

interface IngGamePlugin : Plugin {
    val console: UUID
}