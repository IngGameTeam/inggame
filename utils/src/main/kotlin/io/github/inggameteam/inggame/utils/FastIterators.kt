package io.github.inggameteam.inggame.utils

inline fun <T> List<T>.fastForEach(callback: (T) -> Unit) {
    var n = 0
    while (n < size) callback(this[n++])
}

inline fun <T> Array<T>.fastForEach(callback: (T) -> Unit) {
    var n = 0
    while (n < size) callback(this[n++])
}

inline fun <T> List<T>.fastFirstOrNull(predicate: (T) -> Boolean): T? {
    fastForEach { if (predicate(it)) return it }
    return null
}


inline fun <T> Array<T>.fastFirstOrNull(predicate: (T) -> Boolean): T? {
    fastForEach { if (predicate(it)) return it }
    return null
}

inline fun <T> List<T>.fastFirst(predicate: (T) -> Boolean): T {
    fastForEach { if (predicate(it)) return it }
    throw Throwable("collection predicate is not match any")
}

inline fun <T> Array<T>.fastFirst(predicate: (T) -> Boolean): T {
    fastForEach { if (predicate(it)) return it }
    throw Throwable("collection predicate is not match any")
}

