package io.github.inggameteam.alert.api

import java.util.*

interface AlertReceiver<T> {

    fun receive(sender: UUID, t: T, alert: Alert<T>): Boolean

}