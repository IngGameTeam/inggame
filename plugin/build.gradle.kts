dependencies {
    listOf(
        ":utils",
        ":mongodb",
        ":component",
        ":component-view",
        ":player",
        ":minigame",
        ":guiapi",
        ":command"
    ).map(::project).forEach { api(it); testApi(it) }
}