package io.github.inggameteam.player

import io.github.inggameteam.api.IngGamePlugin
import org.bukkit.entity.Player
import java.util.*

interface PlayerPlugin : IngGamePlugin {

    val playerRegister: GPlayerRegister
    operator fun get(key: Player) = get(key.uniqueId)
    operator fun get(key: UUID) = playerRegister[key]
}