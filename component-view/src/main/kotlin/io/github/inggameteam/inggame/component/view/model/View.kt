package io.github.inggameteam.inggame.component.view.model

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService
import io.github.inggameteam.inggame.component.delegate.get
import io.github.inggameteam.inggame.component.view.wrapper.Selector
import io.github.inggameteam.inggame.component.view.wrapper.SelectorImp
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.entity.Player
import org.koin.core.Koin
import org.koin.core.qualifier.named

interface View {

    val app: Koin
    val plugin: IngGamePlugin

    val player: Player

    fun getSelector(name: String): Selector {
        return (app.get<ComponentService>(named("view-player")) as LayeredComponentService)
            .apply { setParents(player.uniqueId, emptyList()) }
            .apply { addParents(player.uniqueId, name) }
            .get(player.uniqueId, ::SelectorImp)

    }
}

