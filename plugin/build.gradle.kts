dependencies {
    listOf(
        ":utils",
        ":mongodb",
        ":component",
        ":player",
        ":minigame"
    ).forEach { api(project(it)) }
}