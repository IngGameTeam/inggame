package io.github.inggameteam.inggame.utils

import com.eatthepath.uuid.FastUUID
import java.util.*

fun UUID.fastToString() = FastUUID.toString(this)
fun String.fastUUID() = FastUUID.parseUUID(this)

fun randomUUID() = Random().run { UUID(nextLong(), nextLong()) }