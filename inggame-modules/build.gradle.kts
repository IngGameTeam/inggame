subprojects {
    if (version == "unspecified") {
        version = rootProject.version
    }

}

dependencies {
    file("/").listFiles()?.filter { it.isDirectory && it.name.startsWith("${rootProject.name}-") }?.forEach { file ->
        api(project(":${project.name}:${file.name}"))
    }
}

tasks.withType<Jar> {
    dependsOn(*childProjects.values.map { it.tasks.jar }.toTypedArray())
}
