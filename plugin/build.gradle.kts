dependencies {
    listOf(
        ":utils",
        ":mongodb",
        ":component",
        ":player",
        ":minigame",
        ":guiapi",
        ":command"
    ).forEach { api(project(it)) }
}