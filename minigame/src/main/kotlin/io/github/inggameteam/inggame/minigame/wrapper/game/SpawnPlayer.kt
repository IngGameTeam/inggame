package io.github.inggameteam.inggame.minigame.wrapper.game

import io.github.inggameteam.inggame.component.PropWrapper
import io.github.inggameteam.inggame.component.delegate.Wrapper
import io.github.inggameteam.inggame.component.model.LocationModel
import org.bukkit.GameMode

@PropWrapper
interface SpawnPlayer : Wrapper {

    val gameMode: GameMode
    val spawn: LocationModel


}