package io.github.inggameteam.inggame.minigame.component

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.qualifier.named

class GameResourceRepository : KoinComponent {
    val gameResource = get<ComponentService>(named("game-resource"))
}