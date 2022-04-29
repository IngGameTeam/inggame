package io.github.inggameteam.alert.api

import java.util.*
import kotlin.collections.HashMap

open class Alert<RECEIVER>(
    protected var map: Map<String, String> = HashMap(),
) {
    open fun send(
        sender: UUID, t: RECEIVER, vararg args: Any) {
        println(map.map { "${it.key}: ${it.value}".format(*args) })
    }
}
