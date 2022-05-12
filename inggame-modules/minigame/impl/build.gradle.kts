dependencies {
    listOf(
        project(":inggame-modules:minigame:api"),
        project(":inggame-modules:minigame:handle"),
    ).forEach {
        compileOnly(it)
        testCompileOnly(it)
    }
}
