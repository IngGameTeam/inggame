package io.github.inggameteam.plugin.angangang.handler

import com.rylinaux.plugman.util.PluginUtil
import io.github.inggameteam.api.IngGamePlugin
import io.github.inggameteam.scheduler.ITask
import io.github.inggameteam.scheduler.delay
import org.bukkit.Bukkit

class ReloadWatchDog(plugin: IngGamePlugin) {


    init {
        ITask.repeat(plugin, 1, 1, {
            if (Bukkit.getServer().updateFolderFile.listFiles()?.isNotEmpty() == true) {
                {
                    try {
                        PluginUtil.reload(plugin)
                    } catch(_: ClassNotFoundException) {
                        //PlugManX does not exist
                    }
//                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "plugman reload ${plugin.name}")
                }.delay(plugin, 20 * 2)
            }
        })
    }

}