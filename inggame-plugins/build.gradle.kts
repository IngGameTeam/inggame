val pluginProject = project

subprojects {

    tasks.withType<Jar> {
        dependsOn(tasks.processResources)
        archiveFileName.set("${project.name}.jar")
        doLast {
            copy {
                val sep = File.separator
                from("${buildDir.absolutePath}${sep}libs$sep${project.name}.jar")
                into("${pluginProject.buildDir.absolutePath}${sep}dist")
            }
        }
    }

    if (version == "unspecified") {
        version = rootProject.version
    }


    tasks {
        processResources {
            filesMatching("**/*.yml") {
                expand(rootProject.properties)
            }
        }
    }

    dependencies {
//        implementation("io.github.inggameteam:inggame-modules:+")
        implementation(project(":inggame-modules"))
    }

}

tasks.withType<Jar> {
    dependsOn(*childProjects.values.map { it.tasks.jar }.toTypedArray())
}
