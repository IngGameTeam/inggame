package io.github.inggameteam.player

import io.github.inggameteam.alert.api.Alert
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.ArrayList

open class GPlayerList(c: Collection<GPlayer> = Collections.emptyList()) : ArrayList<GPlayer>(c) {
    override fun toString() = if (isEmpty()) "empty" else joinToString(", ")
}

fun Collection<Player>.receiveAll(sender: UUID, alert: Alert<Player>, vararg args: Any) {
    forEach { gPlayer -> alert.send(sender, gPlayer, *args)}
}
