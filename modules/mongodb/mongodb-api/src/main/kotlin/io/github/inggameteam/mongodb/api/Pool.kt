package io.github.inggameteam.mongodb.api

import java.util.*

interface Pool<DATA> {

    fun commit(data: DATA)

    fun pool(uuid: UUID): DATA

}