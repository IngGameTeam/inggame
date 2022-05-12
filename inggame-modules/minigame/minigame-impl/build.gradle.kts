dependencies {
    listOf(
        project(":inggame-modules:minigame:minigame-api"),
        project(":inggame-modules:minigame:minigame-handle"),
        project(":inggame-modules:minigame:minigame-ui"),
    ).forEach {
        compileOnly(it)
        testCompileOnly(it)
    }
}
