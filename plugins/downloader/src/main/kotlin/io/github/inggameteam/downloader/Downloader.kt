package io.github.inggameteam.downloader

import org.apache.commons.io.FileUtils
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.net.URL

@Suppress("unused")
class Downloader : JavaPlugin(), CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        sender.sendMessage("Downloading...")
        if (args.size >= 1) {
            val plugin = Bukkit.getPluginManager().getPlugin(args[0])
            if (plugin !== null) download(plugin)
            else FileUtils.copyURLToFile(URL(args[0]), File("plugins/update"))

        }
        sender.sendMessage("Downloaded")
        return true
    }

    override fun onEnable() {
        getCommand("downloader")?.setExecutor(this)
    }


}
