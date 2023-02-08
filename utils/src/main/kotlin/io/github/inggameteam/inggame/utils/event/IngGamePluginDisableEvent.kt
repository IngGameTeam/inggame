package io.github.inggameteam.inggame.utils.event

import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class IngGamePluginDisableEvent(val plugin: IngGamePlugin) : Event() {
    override fun getHandlers(): HandlerList { return HANDLERS }
    companion object {
        @JvmStatic
        val HANDLERS = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList { return HANDLERS }
    }

}
