import java.io.File
import java.util.concurrent.TimeUnit

object ProjectVersion {

    val gitTag get() = runCommand("git describe --abbrev=0 --tags")
    val gitCommitId get() = runCommand("git rev-parse --short=8 HEAD")

    private fun runCommand(cmd: String, workingDir: File = File("./")): String = cmd.run {
        val parts = this.split("\\s".toRegex())
        val proc = ProcessBuilder(*parts.toTypedArray())
            .directory(workingDir)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start()

        proc.waitFor(1, TimeUnit.MINUTES)
        return proc.inputStream.bufferedReader().readText().trim()
    }

}