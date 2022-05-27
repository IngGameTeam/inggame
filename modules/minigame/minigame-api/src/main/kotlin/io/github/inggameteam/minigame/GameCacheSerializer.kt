package io.github.inggameteam.minigame

import io.github.inggameteam.utils.YamlUtil
import java.io.File

object GameCacheSerializer {

    val GamePlugin.file get() = File(dataFolder, "last-game.yml")

    fun serialize(plugin: GamePlugin) {
        val sections = YamlUtil.getSections(plugin.file)

    }

    fun deserialize(plugin: GamePlugin) {

    }

}