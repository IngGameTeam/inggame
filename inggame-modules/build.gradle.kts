subprojects {
    if (version == "unspecified") {
        version = rootProject.version
    }

}

dependencies {
    file("/").listFiles()?.filter { it.isDirectory && it.name.startsWith("${rootProject.name}-") }?.forEach { file ->
        api(project(":${name}:${file.name}"))
    }
}
