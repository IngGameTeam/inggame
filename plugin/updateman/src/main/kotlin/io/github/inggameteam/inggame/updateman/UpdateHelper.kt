package io.github.inggameteam.inggame.updateman

import io.github.inggameteam.inggame.plugman.util.PluginUtil
import io.github.inggameteam.inggame.utils.runNow
import org.eclipse.jgit.api.Git
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.*
import java.util.concurrent.Executors
import java.util.function.Consumer

class UpdateHelper {

    fun updateGit(settings: UpdateSettings): Boolean = settings.run {
        if (File(gitDir, ".git").exists()) {
            Git.open(File(gitDir, ".git"))
                .pull().call().fetchResult.trackingRefUpdates.isNotEmpty()
        } else {
            gitDir.mkdir()
            Git.cloneRepository()
                .setBranch(branchName)
                .setCloneAllBranches(false)
                .setURI(gitUrl)
                .setDirectory(gitDir)
                .call()
            true
        }
    }

    fun deploy(settings: UpdateSettings): Unit = settings.run {
        ;{
        destinyFile.parentFile.mkdir()
        backupDir.mkdir()
        println(outputFileName)
        try {
            Files.copy(
                destinyFile.toPath(),
                backupDir.toPath(),
                StandardCopyOption.REPLACE_EXISTING,
            )
            Files.copy(
                outputFile.toPath(),
                destinyFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING
            )
            PluginUtil.reload(plugin)
            if (pluginOrNull === null) {
                PluginUtil.unload(plugin)
                destinyFile.deleteOnExit()
                Files.copy(
                    backupFile.toPath(),
                    destinyFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
                )
            }
            backupDir.deleteOnExit()
        } catch (e: Throwable) {
            e.printStackTrace()
        }

    }.runNow(plugin)
    }

    fun runBuild(settings: UpdateSettings): Unit = settings.run {

        val isWindows = System.getProperty("os.name").lowercase(Locale.getDefault()).startsWith("windows")
        val builder = ProcessBuilder()
        if (isWindows) {
            builder.command("cmd.exe", "/c", bashCmd)
        } else {
            builder.command("sh", "-c", bashCmd)
        }
        builder.directory(gitDir)
        val process = builder.start()
        val streamGobbler = StreamGobbler(process.inputStream, plugin.logger::info)
        val future = Executors.newSingleThreadExecutor().submit(streamGobbler)
        future.get()
    }

    class StreamGobbler(private val inputStream: InputStream, private val consumer: Consumer<String>) : Runnable {
        override fun run() {
            BufferedReader(InputStreamReader(inputStream)).lines()
                .forEach(consumer)
        }
    }
}