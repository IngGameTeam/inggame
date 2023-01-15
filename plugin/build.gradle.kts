dependencies {
    listOf(
        ":utils",
        ":mongodb",
        ":component",
        ":player",
        ":minigame",
        ":guiapi",
        ":command"
    ).map(::project).forEach { api(it); testApi(it) }
}