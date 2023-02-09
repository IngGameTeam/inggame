val excludes = listOf("buildSrc")

fun recruit(dir: File) {
    dir.listFiles(File::isDirectory)
        .forEach {
            recruit(it)
            if (File(it, "build.gradle.kts").exists() && !excludes.contains(it.name)) {
                val name = it.name
                include(name)
                findProject(":$name")!!.apply {
                    projectDir = it
                }
            }
        }
}

recruit(settingsDir)
