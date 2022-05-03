package io.github.inggameteam.minigame

import io.github.inggameteam.utils.IntVector
import kotlin.collections.HashMap
import kotlin.test.assertTrue

class GameSupplierRegister(
    gamePlugin: GamePlugin,
    vararg function: (GamePlugin, IntVector) -> Game
) : HashMap<String, (GamePlugin, IntVector) -> Game>() {
    init {
        function.forEach { put(it(gamePlugin, IntVector()).name, it) }
    }

    override fun get(key: String) =
        super.get(key).apply { assertTrue(this === null, "Game $key doesn't exist") }!!
}
