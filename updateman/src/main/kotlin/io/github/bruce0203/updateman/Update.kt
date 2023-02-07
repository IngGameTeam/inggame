package io.github.bruce0203.updateman

import io.github.inggameteam.inggame.plugman.util.PluginUtil
import org.bukkit.Bukkit
import org.eclipse.jgit.api.Git
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*
import java.util.concurrent.Executors
import java.util.function.Consumer
import kotlin.concurrent.thread
import kotlin.io.path.name


class Update(
    key: String,
    plugin: Plugin,
    pluginName: String,
    gitURL: String,
    dir: File,
    cmd: String,
    out: String,
    branch: String,
) {

    init {
        if (plugin.semaphore[key] !== null) {
            throw AssertionError("")
        }
        thread {

            plugin.semaphore[key] = true
            var isCloned = false
            val git: Git = if (File(dir, ".git").exists()) {
                Git.open(File(dir, ".git"))
            } else {
                dir.mkdir()
                isCloned = true
                Git.cloneRepository()
                    .setBranch(branch)
                    .setCloneAllBranches(false)
                    .setURI(gitURL)
                    .setDirectory(dir)
                    .call()
            }
            if (isCloned || git.pull().call().fetchResult.trackingRefUpdates.isNotEmpty()) {
                val isWindows = System.getProperty("os.name")
                    .lowercase(Locale.getDefault()).startsWith("windows")


                val builder = ProcessBuilder()
                if (isWindows) {
                    builder.command("cmd.exe", "/c", cmd)
                } else {
                    builder.command("sh", "-c", cmd)
                }
                builder.directory(dir)
                val process = builder.start()
                val streamGobbler = StreamGobbler(process.inputStream, System.out::println)
                val future = Executors.newSingleThreadExecutor().submit(streamGobbler)
                println("Updating...")
                future.get()
                println("Update Done!")

                Bukkit.getScheduler().runTask(plugin){ _ ->
                    val pl = Bukkit.getPluginManager().getPlugin(pluginName)
                    val path = if (pl !== null) File("plugins/update/${Paths.get(out).name}").toPath()
                    else File("plugins/${Paths.get(out).name}").toPath()
                    Files.copy(
                        File(dir, out).toPath(),
                        path,
                        StandardCopyOption.REPLACE_EXISTING
                    )
                    if (pl == null) PluginUtil.load(pluginName)
                    else {
                        PluginUtil.reload(pl)
                    }
                    plugin.semaphore.remove(key)
                }
            } else {
                plugin.semaphore.remove(key)
            }
        }
    }

    private class StreamGobbler(private val inputStream: InputStream, val consumer: Consumer<String>) : Runnable {
        override fun run() {
            BufferedReader(InputStreamReader(inputStream)).lines()
                .forEach(consumer)
        }
    }

}