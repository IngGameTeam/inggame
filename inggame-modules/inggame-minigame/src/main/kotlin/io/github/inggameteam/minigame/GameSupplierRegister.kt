package io.github.inggameteam.minigame

import io.github.inggameteam.utils.IntVector
import kotlin.collections.HashMap

class GameSupplierRegister(
    vararg function: (IntVector) -> Game
) : HashMap<String, (IntVector) -> Game>() {
    init {
        function.forEach { put(it(IntVector()).name, it) }
    }

    override fun get(key: String) =
        super.get(key).apply { assert (this === null) { "Game $key doesn't exist" } }!!
}
