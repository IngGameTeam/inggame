package io.github.inggameteam.inggame.minigame.base.spawnplayer

import io.github.inggameteam.inggame.component.model.LocationModel
import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.minigame.base.sectional.Sectional
import io.github.inggameteam.inggame.minigame.base.sectional.SectionalImp
import org.bukkit.GameMode

class SpawnPlayerImp(wrapper: Wrapper) : SpawnPlayer, Sectional by SectionalImp(wrapper) {

    override val gameMode: GameMode by nonNull
    override val spawn: LocationModel by nonNull

}