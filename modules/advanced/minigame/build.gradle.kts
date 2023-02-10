dependencies {
    implementation(project(mapOf("path" to ":utils")))
}
projectDependencies(
    utils,
    component_common,
    mongodb,
    player,
    world,
)