package io.github.inggameteam.minigame

import io.github.inggameteam.utils.Intvector
import kotlin.collections.HashMap

class GameSupplierRegister(
    vararg function: (Intvector) -> Game
) : HashMap<String, (Intvector) -> Game>() {
    init {
        function.forEach { put(it(Intvector()).name, it) }
    }
}
