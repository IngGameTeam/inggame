package io.github.inggameteam.utils

open class ListWithToString<T> : ArrayList<T> {
    constructor() : super()
    constructor(c: Collection<T>) : super(c)

    override fun toString() = if (isEmpty()) "empty" else joinToString(", ")
}

fun <T> Collection<T>.listWithToString() = ListWithToString(this)