package io.github.inggameteam.inggame.utils

import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.CopyOnWriteArraySet

fun Collection<*>.noBraceToString() = if (isEmpty()) "empty" else joinToString(", ")

fun <T> List<T>.listWithToString() = ListWithToString(this)

open class ListWithToString<T>(list: List<T> = emptyList()) : ArrayList<T>(list) {
    override fun toString() = noBraceToString()
}

open class SafeListWithToString<T>(list: List<T> = emptyList()) : CopyOnWriteArrayList<T>(list) {
    override fun toString() = noBraceToString()
}

open class SetWithToString<T>(list: List<T>) : HashSet<T>(list) {
    override fun toString() = noBraceToString()
}

open class SafeSetWithToString<T>(list: Collection<T>? = emptyList()) : CopyOnWriteArraySet<T>(list) {
    override fun toString() = noBraceToString()
}