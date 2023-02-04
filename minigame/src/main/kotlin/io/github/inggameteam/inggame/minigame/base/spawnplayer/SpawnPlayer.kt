package io.github.inggameteam.inggame.minigame.base.spawnplayer

import io.github.inggameteam.inggame.component.delegate.Wrapper
import io.github.inggameteam.inggame.component.model.LocationModel
import org.bukkit.GameMode

interface SpawnPlayer : Wrapper {

    val gameMode: GameMode
    val spawn: LocationModel


}