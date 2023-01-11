import java.io.File
import java.util.concurrent.TimeUnit

object ProjectVersion {

    private fun String.runCommand(workingDir: File = File("./")): String {
        val parts = this.split("\\s".toRegex())
        val proc = ProcessBuilder(*parts.toTypedArray())
            .directory(workingDir)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start()

        proc.waitFor(1, TimeUnit.MINUTES)
        return proc.inputStream.bufferedReader().readText().trim()
    }

    val gitTag = "git describe --abbrev=0 --tags".runCommand()
    val gitCommitId = "git rev-parse --short=8 HEAD".runCommand()


}