package io.github.inggameteam.mongodb.impl

import java.util.*

interface Pool<DATA> {

    fun upsert(data: DATA)

    fun pool(uuid: UUID): DATA

}