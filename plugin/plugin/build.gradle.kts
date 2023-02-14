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

tasks.withType<Jar> {
    archiveFileName.set("${rootProject.name}.jar")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("${rootProject.name}.jar")
}