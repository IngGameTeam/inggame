val core = project(":${rootProject.name}-core")

subprojects {
    if (version == "unspecified") {
        version = rootProject.version
    }

}

dependencies {
    file("/").listFiles()?.filter { it.isDirectory && it.name.startsWith("${rootProject.name}-") }?.forEach { file ->
        implementation(project(":${name}:${file.name}"))
    }
}
