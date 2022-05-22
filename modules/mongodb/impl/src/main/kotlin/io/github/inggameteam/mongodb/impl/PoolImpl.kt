package io.github.inggameteam.mongodb.impl

import io.github.inggameteam.api.IngGamePlugin

abstract class PoolImpl<DATA>(plugin: IngGamePlugin) : Pool<DATA> {

    val pool = ArrayList<DATA>()

    init { plugin.addDisableEvent { pool.forEach(::upsert) } }
}