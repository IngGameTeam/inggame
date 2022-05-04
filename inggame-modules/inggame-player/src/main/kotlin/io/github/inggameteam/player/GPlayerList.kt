package io.github.inggameteam.player

import io.github.inggameteam.utils.ListWithToString
import java.util.*

open class GPlayerList(c: Collection<GPlayer> = Collections.emptyList()) : ListWithToString<GPlayer>(c)
fun Collection<GPlayer>.toPlayerList() = GPlayerList(this)
