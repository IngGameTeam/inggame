package io.github.inggameteam.inggame.component.view.model

import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.entity.Player
import org.koin.core.Koin

class ViewImp(override val app: Koin, override val plugin: IngGamePlugin, override val player: Player) : View