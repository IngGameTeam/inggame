dependencies {
    implementation(project(mapOf("path" to ":utils")))
}
projectDependencies(
    utils,
    component,
    mongodb,
    player,
    world
)