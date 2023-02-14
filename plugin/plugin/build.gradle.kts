projectDependencies(
    utils,
    mongodb,
    component_common,
    component_view,
    player,
    minigame,
    minigame_view,
    guiapi,
    command,
    world,
    plugman,
    item,
    updateman,
    includeJar = true,
)
dependencies {
    implementation(project(mapOf("path" to ":minigame-view")))
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("${rootProject.name}.jar")
}