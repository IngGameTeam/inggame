package io.github.inggameteam.inggame.minigame.wrapper.game

import io.github.inggameteam.inggame.component.delegate.Wrapper
import io.github.inggameteam.inggame.component.model.LocationModel
import org.bukkit.GameMode

class SpawnPlayerImp(wrapper: Wrapper) : SpawnPlayer, Sectional by SectionalImp(wrapper) {

    override val gameMode: GameMode by nonNull
    override val spawn: LocationModel by nonNull

}