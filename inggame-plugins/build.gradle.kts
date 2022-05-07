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


    tasks {
        processResources {
            repeat(2) {
                filesMatching("**/*.yml") {
                    expand(HashMap(rootProject.properties).apply { putAll(pluginProject.properties) })
                }
            }
        }
    }

/*    dependencies {
        implementation(project(":inggame-modules"))
    }*/

}

tasks.withType<Jar> {
    dependsOn(*childProjects.values.map { it.tasks.jar }.toTypedArray())
}
