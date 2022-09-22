package io.github.inggameteam.plugin.testbot

import io.github.inggameteam.api.IngGamePluginImpl
import org.bukkit.Bukkit
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import javax.script.Invocable
import javax.script.ScriptEngineManager


class Plugin : IngGamePluginImpl() {
    override fun onEnable() {
        Bukkit.getScheduler().runTaskLater(this, Runnable {
            val manager = ScriptEngineManager()
            val engine = manager.getEngineByName("JavaScript")
            engine.eval(Files.newBufferedReader(Paths.get("plugins/$name/index.js"), StandardCharsets.UTF_8))
            val inv = engine as Invocable
            inv.invokeFunction("main")

        }, 1L)
    }
}