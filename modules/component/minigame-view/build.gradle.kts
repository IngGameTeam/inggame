dependencies {
    implementation(project(mapOf("path" to ":utils")))
}
projectDependencies(
    utils,
    component_common,
    item,
    guiapi,
    minigame_common,
    player,
)