if((JavaVersion.current() != JavaVersion.VERSION_17)) {
    throw kotlin.NullPointerException("Java 17 is required")
}

rootProject.name = "inggame"

val srcDir = "src"
val buildDir = "build"
val excludeFileNames = listOf(srcDir, buildDir)
fun circuitDir(dir: File) {
    dir.listFiles()?.forEach {
        val isRootDir = it.parentFile.equals(rootProject.projectDir)
        if (it.isDirectory &&
            (isRootDir && it.name.startsWith("${rootProject.name}-") || !isRootDir && !excludeFileNames.contains(it.name))) {
            val moduleName = ":" + it.relativeTo(rootDir).path.replace(File.separator, ":")
            include(moduleName)
            circuitDir(it)
        }
    }
}
circuitDir(File(rootDir, "modules"))
circuitDir(File(rootDir, "plugins"))
circuitDir(File(rootDir, "publish"))
