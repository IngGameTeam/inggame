package io.github.inggameteam.minigame

import kotlin.collections.HashMap

class GameSupplierRegister(
    vararg function: (Sector) -> Game
) : HashMap<String, (Sector) -> Game>() {
    init {
        function.forEach { put(it(Sector()).name, it) }
    }
}
