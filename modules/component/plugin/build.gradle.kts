plugins {
    id("kr.entree.spigradle") version "2.4.3"
}

projectDependencies(
    utils,
    mongodb,
    component_common,
    component_view,
    player,
    minigame_common,
    minigame_view,
    guiapi,
    command,
    world,
    plugman,
    item,
    updateman,
    inggame_app,
    includeJar = true,
)
tasks.forEach { it.outputs.cacheIf { true } }
spigot {
    name = rootProject.name
    this.version = "${project.version}"
    main = "io.github.inggameteam.inggame.plugin.Plugin"
    apiVersion = "1.19"
    authors("Bruce0203", "chomade", "Boxgames1")
    softDepends = listOf("FastAsyncWorldEdit")
    libraries = listOf(
        "org.jetbrains.kotlin:kotlin-stdlib:${Dependency.Kotlin.Version}",
        "org.jetbrains.kotlin:kotlin-reflect:${Dependency.Kotlin.Version}",
        "org.mongodb:mongodb-driver-sync:${Dependency.MongoDB.Version}",
        "com.eatthepath:fast-uuid:0.2.0",
        "io.insert-koin:koin-core-jvm:${Dependency.Koin.Version}",
        "io.github.bruce0203:nbt-api:6",
        "io.github.bruce0203:jgit:5"
    )
    commands {
        create("ing") {
            permission = "inggame.admin"
        }
    }
    permissions {
        create("inggame.admin") {
            defaults = "op"
        }
    }
}

tasks.withType<Jar> {
    archiveFileName.set("${rootProject.name}.jar")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("${rootProject.name}.jar")
}