projectDependencies(
    utils,
    mongodb,
    component,
    component_view,
    player,
    minigame,
    guiapi,
    command,
    world,
    plugman,
    includeJar = true,
)

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("${rootProject.name}.jar")
    classifier = null
}