dependencies {
    listOf(
        ":utils",
        ":mongodb",
        ":component",
        ":player",
        ":minigame"
    ).forEach { compileOnly(project(it)) }
}