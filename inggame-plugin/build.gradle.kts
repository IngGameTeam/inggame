val core = project(":${rootProject.name}-core")

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


    tasks {
        processResources {
            filesMatching("**/*.yml") {
                expand(rootProject.properties)
            }
        }
    }

    dependencies {
        implementation(project(":inggame-modules:inggame-utils"))
        implementation(project(":inggame-modules:inggame-alert"))
        implementation(project(":inggame-modules:inggame-minigame"))
        implementation(project(":inggame-modules:inggame-party"))
        implementation(project(":inggame-modules:inggame-player"))
        implementation(project(":inggame-modules:inggame-scheduler"))
    }
}
dependencies {
    implementation(core)
}


