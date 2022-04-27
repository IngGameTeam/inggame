val core = project(":${rootProject.name}-core")

subprojects {
    apply(plugin = "com.github.johnrengelman.shadow")

    tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        archiveFileName.set("${rootProject.name}.jar")
    }

    if (version == "unspecified") {
        version = rootProject.version
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
