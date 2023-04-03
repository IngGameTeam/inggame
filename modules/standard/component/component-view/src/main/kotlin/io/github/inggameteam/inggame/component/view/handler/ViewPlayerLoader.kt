package io.github.inggameteam.inggame.component.view.handler

import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService
import io.github.inggameteam.inggame.component.helper.LayeredPlayerLoader
import io.github.inggameteam.inggame.component.view.component.ViewPlayer
import io.github.inggameteam.inggame.utils.Helper
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.Bukkit

@Helper
class ViewPlayerLoader(viewHelper: ViewPlayer, plugin: IngGamePlugin) :
    LayeredPlayerLoader(viewHelper as LayeredComponentService, plugin)
{
        init {
            Bukkit.broadcastMessage("ViewPlayerLoader")
        }
    }