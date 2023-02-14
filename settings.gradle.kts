val excludes = listOf("buildSrc")
val buildFile = "build.gradle.kts"

fun recruit(dir: File) {
    dir.listFiles(File::isDirectory)?.forEach {
        recruit(it)
        val name = it.name
        if (File(it, buildFile).exists() && !excludes.contains(name)) {
            include(name)
            findProject(":$name")!!.projectDir = it
        }
    }
}

recruit(settingsDir)
