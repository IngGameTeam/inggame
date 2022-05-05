if((JavaVersion.current() != JavaVersion.VERSION_17)) {
    throw kotlin.NullPointerException("Java 17 is required")
}

rootProject.name = "inggame"

fun circuitDir(dir: File) {
    dir.listFiles()?.forEach {
        if (it.isDirectory && it.name.startsWith("${rootProject.name}-")) {
            println(it.name)
            val moduleName = ":" + it.relativeTo(rootDir).path.replace(File.separator, ":")
            println(moduleName)
            include(moduleName)
            circuitDir(it)
        }
    }
}
circuitDir(rootDir)
