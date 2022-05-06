package io.github.inggameteam.minigame

import io.github.inggameteam.api.PluginHolder
import kotlin.test.assertTrue

class GameSupplierRegister(
    override val plugin: GamePlugin,
    vararg function: (GamePlugin, Sector) -> Game
) : HashMap<String, (GamePlugin, Sector) -> Game>(), PluginHolder<GamePlugin> {
    init {
        function.forEach { put(it(plugin, Sector()).name, it) }
    }

    override fun get(key: String) =
        super.get(key).apply { assertTrue(this !== null, "Game $key doesn't exist") }!!
}
