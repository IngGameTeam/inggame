package io.github.inggameteam.minigame

import io.github.inggameteam.api.PluginHolder
import kotlin.test.assertTrue

class GameSupplierRegister(
    override val plugin: GamePlugin,
    vararg function: (GamePlugin) -> Game
) : HashMap<String, (GamePlugin) -> Game>(), PluginHolder<GamePlugin> {
    init {
        function.forEach { put(it(plugin).name, it) }
    }

    override fun get(key: String) =
        super.get(key).apply { assertTrue(this !== null, "Game $key doesn't exist") }!!
}
