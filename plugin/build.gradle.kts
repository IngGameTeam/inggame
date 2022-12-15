dependencies {
        compileOnly("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT")
}
tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        dependsOn(tasks.processResources)
        archiveFileName.set("${rootProject.name}.jar")

        doLast {
                copy {
                        val sep = File.separator
                        from("${buildDir.absolutePath}${sep}libs$sep${project.name}.jar")
                        into("${project.buildDir.absolutePath}${sep}dist")
                }
        }
}

tasks {
        processResources {
                repeat(2) {
                        filesMatching("**/*.yml") {
                                expand(HashMap(rootProject.properties)
                                        .apply { putAll(project.properties) }
                                        .apply { put("version", rootProject.version)})
                        }
                }
        }
}

tasks.withType<Jar> {
        dependsOn(*childProjects.values.map { it.tasks.jar }.toTypedArray())
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        dependsOn(*childProjects.values.map { it.tasks.shadowJar }.toTypedArray())
}
