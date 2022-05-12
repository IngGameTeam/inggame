dependencies {
    listOf(
        project(":inggame-modules:minigame:api"),
        project(":inggame-modules:minigame:handle"),
        project(":inggame-modules:minigame:ui"),
    ).forEach {
        compileOnly(it)
        testCompileOnly(it)
    }
}
