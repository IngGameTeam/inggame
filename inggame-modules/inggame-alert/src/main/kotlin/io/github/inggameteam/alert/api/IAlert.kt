package io.github.inggameteam.alert.api

import java.util.*
import kotlin.collections.HashMap

open class Alert<RECEIVER>(
    protected var map: Map<String, String> = HashMap(),
) {
    open fun send(sender: UUID?, t: RECEIVER, vararg args: Any) {
        println(map.map { "${it.key}: ${it.value}".format(*args) })
    }
    fun send(sender: UUID?, ts: List<RECEIVER>, vararg args: Any) = ts.forEach { send(sender, it, *args) }
    fun send(t: RECEIVER, vararg args: Any) = send(null, t, *args)
    fun send(ts: List<RECEIVER>, vararg args: Any) = send(null, ts, *args)
}
