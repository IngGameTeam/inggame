val pluginProject = project

subprojects {
    apply(plugin = "com.github.johnrengelman.shadow")

    tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        archiveFileName.set("${project.name}.jar")
    }

    tasks.withType<Jar> {
        archiveFileName.set("${project.name}.jar")
    }

    if (version == "unspecified") {
        version = rootProject.version
    }

    tasks.register<Copy>("dist") {
        dependsOn(tasks.jar)
        val sep = File.separator
        from("${buildDir.absolutePath}${sep}libs$sep${project.name}.jar")
        into("${pluginProject.buildDir.absolutePath}${sep}dist")
    }

    tasks {
        processResources {
            filesMatching("**/*.yml") {
                expand(rootProject.properties)
            }
        }
    }

    dependencies {
        implementation(project(":${rootProject.name}-modules"))
    }

}

