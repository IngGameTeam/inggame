package io.github.inggameteam.player

import java.util.*

open class GPlayerList(c: Collection<GPlayer> = Collections.emptyList()) : ArrayList<GPlayer>(c) {
    override fun toString() = if (isEmpty()) "empty" else joinToString(", ")
}
