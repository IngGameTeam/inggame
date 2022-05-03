package io.github.inggameteam.player

import java.util.*
import kotlin.collections.ArrayList

open class GPlayerList(c: Collection<GPlayer> = Collections.emptyList()) : ArrayList<GPlayer>(c) {
    override fun toString() = if (isEmpty()) "empty" else joinToString(", ")
}
