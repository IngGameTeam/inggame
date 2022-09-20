import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val pluginProject = project

subprojects {

    tasks.withType<ShadowJar> {
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
                    expand(HashMap(rootProject.properties)
                        .apply { putAll(pluginProject.properties) }
                        .apply { put("version", rootProject.version)})
                }
            }
        }
    }

/*    dependencies {
        implementation(project(":modules"))
    }*/

}

tasks.withType<Jar> {
    dependsOn(*childProjects.values.map { it.tasks.jar }.toTypedArray())
}

tasks.withType<ShadowJar> {
    dependsOn(*childProjects.values.map { it.tasks.shadowJar }.toTypedArray())
}
